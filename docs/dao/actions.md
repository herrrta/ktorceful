# Actions

Implementing `EntityAction` allows you to create methods inside your route class that act as 
separate endpoints.

Actions all use the POST http method since it assumes you are passing in a list of entities to
perform an action on.

## Defining an action

An action must be annotated using the `Action` annotation class.

Each action should have `(RoutingCall, List<Entity>)` as parameters.

```kotlin
@Resource("user")
class UserRoute: APIRoute<User>() {
    //...

    @Action
    private fun disable(call: RoutingCall, users: List<User>) { 
        users.forEach {
            it.isActive = false
        }
    }

    @Action
    private fun activate(call: RoutingCall, users: List<User>) {
        users.forEach {
            it.isActive = true
        }
    }
}
```


## Calling an action

To call an action you can use the following url pattern:

- `POST: {entity}/{action}/`

In the user example above we can use the following urls:

- `POST: user/disable/`
- `POST: user/activate/`


