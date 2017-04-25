package com.anongsinbook.helloworld;

import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.view.View;

/**
 * Created by intar on 14/2/2560.
 */

public class CustomViewSavedState extends View.BaseSavedState {

    private boolean blue;

    public boolean isBlue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public CustomViewSavedState(Parcel source) {
        super(source);
        // Read back
        blue = source.readInt() == 1;
    }

    public CustomViewSavedState(Parcelable superState) {
        super(superState);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        // Write var here
        out.writeInt(blue ? 1 : 0);
    }

    public  static  final  Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new CustomViewSavedState(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new CustomViewSavedState[size];
        }
    };
}