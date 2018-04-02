package com.senla.retrofit.reflection;

import android.util.Log;

public class Tester {

    @TesterAttribute(info = "Some attribute")
    private String param;

    protected int protParam = 42;

    public Tester(String param) {
        this.param = param;
    }

    @TesterMethod(description = "Some public method")
    public void doPublic() {
        Log.e("TAG", "protected: " + param);
    }

    protected void doProtected() {
        Log.e("TAG", "protected: " + param + " (" + String.valueOf(protParam) + ")");
    }

    @TesterMethod(description = "Some private method", isInner = true)
    private void doPrivate() {
        Log.e("TAG", "private: " + param);
    }
}
