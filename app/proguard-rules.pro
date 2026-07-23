# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontobfuscate
-dontshrink

# Keep all model classes
-keep class com.discipline.data.** { *; }

# Keep Gson models
-keepclasseswithmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Preserve line numbers for crash reports
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
