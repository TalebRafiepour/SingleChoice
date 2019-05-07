# SingleChoice
single choice view for android

![alt text](https://github.com/TalebRafiepour/SingleChoice/blob/master/SingleChoiceGif.gif)




Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
   ``` 
  Step 2. Add the dependency
  ```
  dependencies {
	        implementation 'com.github.TalebRafiepour:SingleChoice:0.2'
	}
```
Usage: 
```
 <com.taleb.widget.SingleChoiceView
        android:id="@+id/singleChoiceView"
        android:layout_width="match_parent"
        android:layout_margin="20dp"
        android:layout_height="48dp"
        android:layout_gravity="center|top"
        app:sc_choices="@array/choices"
        app:sc_select_position="1"
        app:sc_choice_text_size="14sp"
        app:sc_choice_text_font="fonts/Raleway-Medium.ttf"/>
