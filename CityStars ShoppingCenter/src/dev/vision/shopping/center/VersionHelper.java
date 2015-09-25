package dev.vision.shopping.center;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;

class VersionHelper
{
    static void refreshActionBarMenu(ActionBarActivity activity)
    {
        activity.supportInvalidateOptionsMenu();
        activity.invalidateOptionsMenu();
    }
}