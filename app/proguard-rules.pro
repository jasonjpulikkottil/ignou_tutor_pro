
-keepattributes Signature
-keepattributes *Annotation*

-dontwarn sun.misc.Unsafe

-keepclassmembers class com.jdots.ignoupro.conversation.data_model.** {*;}
-keepclassmembers class com.jdots.ignoupro.conversation_list.data_model.** {*;}
-keepclassmembers class com.jdots.ignoupro.global.data_model.** {*;}
-keepclassmembers class com.jdots.ignoupro.user.data_model.** {*;}

-keep public class com.jdots.ignoupro.login.**

-keep class com.android.vending.billing.**