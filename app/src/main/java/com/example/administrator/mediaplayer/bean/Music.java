package com.example.administrator.mediaplayer.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by acfun on 2016/12/2.
 */

public class Music implements Parcelable {

    private String name;
    private String author;
    private String url;
    private String coverImg;

    public Music(String name,String author,String url,String coverImg){
        this.name=name;
        this.author=author;
        this.url=url;
        this.coverImg=coverImg;
    }

    public Music(){

    }

    private Music(Parcel in) {
        this.name=in.readString();
        this.author=in.readString();
        this.url=in.readString();
        this.coverImg=in.readString();
    }

    public String getAuthor() {
        return author;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {

        /**
         * 从序列化对象中创建原始对象
         * @param in
         * @return
         */
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        /**
         * 创建指定长度的原始对象
         * @param size
         * @return
         */
        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    /**
     * 返回当前对象内容描述。
     *  0或1
     *  若含有文件描述符返回1(CONTENTS_FILE_DESCRIPTOR)
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将当前对象写入序列化结构中
     * flag：
     *  1：标示当前对象需要作为返回值返回，不能立即释放资源
     *  0：
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(author);
        dest.writeString(url);
        dest.writeString(coverImg);
    }
}
