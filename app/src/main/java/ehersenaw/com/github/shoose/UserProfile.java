package ehersenaw.com.github.shoose;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfile implements Parcelable {
    String mID;
    String mPW;

    public UserProfile() {
        // Empty constructor
    }

    // Without NAVER
    public UserProfile(String ID, String PW) {
        mID = ID;
        mPW = PW;
    }

    // With NAVER
    public UserProfile(String accessToken) {

    }

    protected UserProfile(Parcel in) {
        mID = in.readString();
        mPW = in.readString();
    }

    public final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel parcel) {
            return new UserProfile(parcel);
        }

        @Override
        public UserProfile[] newArray(int i) {
            return new UserProfile[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(mPW);
    }
}
