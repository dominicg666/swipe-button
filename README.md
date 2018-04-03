# swipe-button

To get a Git project into your build:

# Gradle:



	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
	        compile 'com.github.dominicg666:swipe-button:0.1.0'
	}
  
  # Maven:
  
  
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

	<dependency>
	    <groupId>com.github.dominicg666</groupId>
	    <artifactId>swipe-button</artifactId>
	    <version>0.1.0</version>
	</dependency>
  
  # Example:
  
   <ui.dom.com.Button.Swipe  
   
        android:id="@+id/swipe"
        android:background="@color/colorPrimary"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        app:tez_button_paddingBottom="10dp"
        app:tez_button_paddingTop="10dp"
        app:tez_button_paddingLeft="10dp"
        app:tez_button_paddingRight="10dp"
        app:tez_button_width="60dp"
        app:tez_button_height="60dp"
        app:tez_strock_size="10dp"
        app:tez_top_value="SEND"
        app:tez_bottom_value="RECEIVE"
        >
    </ui.dom.com.Button.Swipe>
