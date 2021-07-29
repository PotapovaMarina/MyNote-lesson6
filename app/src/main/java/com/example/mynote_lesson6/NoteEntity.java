package com.example.mynote_lesson6;


import android.os.Parcel;
import android.os.Parcelable;

public class NoteEntity implements Parcelable {
    public String head;
    public String text;
    public int flag;
    public String date;

    @Override
    public String toString() {
        return head + ' ' + "(" + date + ")";
    }

    public NoteEntity(String head, String text, String date, int flag) {
        this.head = head;
        this.text = text;
        this.date = date;
        this.flag = flag;
    }

    protected NoteEntity(Parcel in) {
        head = in.readString();
        text = in.readString();
        date = in.readString();
        flag = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(head);
        dest.writeString(text);
        dest.writeString(date);
        dest.writeInt(flag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };

    public void setHead(String head) {
        this.head = head;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
