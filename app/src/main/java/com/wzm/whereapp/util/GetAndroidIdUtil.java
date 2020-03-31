package com.wzm.whereapp.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * 描述：
 * 创建者： wangzm
 * 时间：2020/3/30
 * 修改者：
 * 时间：
 */
public class GetAndroidIdUtil {
    /**
     * 在媒体文件中 生成fileName文件
     * 向Mediastore添加内容
     *
     * @param saveFileName 保存文件的名称
     */
    public static String creatUUIDFile(Context context,String saveFileName) {
        String uuidStr="";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, saveFileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
        // TODO: 2019-10-11 IS_PENDING = 1表示对应的item还没准备好
        values.put(MediaStore.Images.Media.IS_PENDING, 1);

        ContentResolver resolver = context.getContentResolver();
        Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);

        Uri uri = resolver.insert(collection, values);

        try {
            //访问 对于单个媒体文件，请使用 openFileDescriptor()。
            ParcelFileDescriptor fielDescriptor = resolver.openFileDescriptor(uri, "w", null);
            FileOutputStream outputStream = new FileOutputStream(fielDescriptor.getFileDescriptor());
            try {
                //讲UUID写入到文件中
                uuidStr = UUID.randomUUID().toString();
                outputStream.write(uuidStr.getBytes());
                outputStream.close();
                Log.d("GetAndroidIdUtil", "写入 uuidStr:" + uuidStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            values.clear();
            values.put(MediaStore.Images.Media.IS_PENDING, 0);          //设置为0
            resolver.update(uri, values, null, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return uuidStr;
    }

    /**
     * 检查文件是否存在
     *
     * @param saveFileName 保存文件的名称
     * @return true 为存在   false为不存在
     */
    private boolean checkUUIDFileByUri(Context context,String saveFileName) {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media._ID
        };
        //查询
        ContentResolver contentResolver = context.getContentResolver();

        // 添加筛选条件
        String selection = MediaStore.Images.Media.DISPLAY_NAME + "=" + "'" + saveFileName + "'";
        Cursor mCursor = contentResolver.query(mImageUri, projection, selection, null, null);

        String getSaveContent = "";
        if (mCursor != null) {
            while (mCursor.moveToNext()) {

                int fileIdIndex = mCursor.getColumnIndex(MediaStore.Images.Media._ID);
                String thumbPath = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon()
                        .appendPath(String.valueOf(mCursor.getInt(fileIdIndex))).build().toString();
                Uri fileUri = Uri.parse(thumbPath);
                try {
                    ParcelFileDescriptor fielDescriptor = contentResolver.openFileDescriptor(fileUri, "r", null);
                    FileInputStream inputStream = new FileInputStream(fielDescriptor.getFileDescriptor());
                    getSaveContent = inputStreamToString(inputStream);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //只有在得到的唯一标识符不为空的情况下才结束循环，否则一直循环
                if (!TextUtils.isEmpty(getSaveContent)) {
                    break;
                }
            }
            mCursor.close();

        }

        return !getSaveContent.equals("");
    }

    /**
     * 流转为字符串
     *
     * @param is 流
     * @return 转换完成的字符串
     */
    public String inputStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
