# easynotificationview
This small kotlin library helps you to show fully customizable notifications in any part of your app without the need to write any boilerplate code.
```kotlin
EasyNotificationView.create(this, R.layout.layout_for_notification).show()
```
In this case you will see smth like this:

![](sample-notification.gif)

### How to customize the notification layout?
Just pass what layout you like to the create function.
### How to customiza the animation?
I've created a couple of basic animations. You can add them this way:
```kotlin
        EasyNotificationView.create(
            this,
            R.layout.layout_for_notification,
            appearAnimator = FadeInAppearAnimator(),
            disappearAnimator = BottomSlideDisappearAnimator()
        ).show()     
```
You can easily create your animation and animate any parameters you want. Just inherit from basic Appear or Disappear animator class and implement required functions.
You can see the example in created animations.

### Changing the default container for the notification.
By default, the container used for the notification is the root view of the context, which is passed in the create function. In this case, you must pass either fragment or activity context, not the application one,
because the application context doesn't have an associated layout with it.
