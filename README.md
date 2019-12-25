# ToDoList_Batch2

Build a fully material To-Do app which help user to view his tasks, add, remove and edit. General project requirements

• Minimum API version 21. • Use Navigation component • Use MDC components • Use MVVM pattern • Use Dagger 2 , RxJava and Data binding • Use sandbox site to mock 4 web services (getTasks , add , edit , remove) • Show loading indicator whenever there is a network request, hide when done. • Error handling: (Create custom Rx retrofit Adapter which help you to override RX Retrofit adapter call back onError() and return (code /message) instead of Expectation).

o No internet connection: show full dialog include message “No Internet connection” with retry button. o Show toast message in case of api error (meaningful message)

• Build base layer which handle network errors, loading indicator across app screens so that not duplicate your code.

Base layer Guidelines:

• Build base fragment to handle error and loading UI. • Build base view model to emit loading flag, errors message to base fragment via LiveData. • Build base Observer which hold view model reference and override onSubscribe() to show loading indicator , override onNext() to hide loading indicator , and override onError() to hide loading indicator and pass error message.

Screen 1:

Task will have o Name o Date / time o Status (initial / finished)

If tasks is empty show in the center of screen (Icon / text) to inform user that there is no tasks

The application displays a list of tasks (name/ date and time / icon with first letter of task) with Fab button to add new task

When long click on task show 2 toolbar actions (finish tasks) and swipe will remove task • Remove will remove task and update list, show confirmation dialog first. • Finish will update task status.

Screen 2: Add task

Add name (use microphone also) and date/time dialogs.

Screen 3: Edit task

User can edit name / date and time, or set flag that task is finished.

Regarding to sandbox , you can save tasks list, add/edit/remove from this list, you can response with api error to see how your app will response.. Please read: https://getsandbox.com/docs/http-request-handling https://getsandbox.com/docs/stateful-responses https://getsandbox.com/docs/error-handling
