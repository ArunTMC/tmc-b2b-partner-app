package com.tmc.tmcb2bpartnerapp.utils;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.core.util.Pair;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.concurrent.TimeUnit;

@SuppressLint("ParcelCreator")
public class RangeDateValidator implements CalendarConstraints.DateValidator {

    private final long minDate;
    private final long maxDate;

    public RangeDateValidator(long minDate, long maxDate) {
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    @Override
    public boolean isValid(long date) {
        return date >= minDate && date <= maxDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(minDate);
        parcel.writeLong(maxDate);
    }

    public static final Parcelable.Creator<RangeDateValidator> CREATOR = new Parcelable.Creator<RangeDateValidator>() {
        @Override
        public RangeDateValidator createFromParcel(Parcel in) {
            return new RangeDateValidator(in.readLong(), in.readLong());
        }

        @Override
        public RangeDateValidator[] newArray(int size) {
            return new RangeDateValidator[size];
        }
    };
}
