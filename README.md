<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="Workout Tracker"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.WorkoutTracker">

        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <activity
            android:name=".activities.AddWorkoutActivity"
            android:exported="false"/>

        <activity
            android:name=".activities.WorkoutListActivity"
            android:exported="false"/>

        <activity
            android:name=".activities.WorkoutDetailActivity"
            android:exported="false"/>

        <activity
            android:name=".activities.AddExerciseActivity"
            android:exported="false"/>

        <activity
            android:name=".activities.StatsActivity"
            android:exported="false"/>

    </application>

</manifest>
