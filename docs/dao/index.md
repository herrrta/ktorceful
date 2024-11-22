# Overview
To create a basic rest api you can implement any of the entity interfaces required for your api

Use the [available interfaces](../api/dao/dev.herrrta.ktorceful.dao.interfaces/index.html) to create a CRUD api for your entity


## Example classes

```kotlin
abstract class APIRoute<E: Any> (
    val parent: API = API() // (1)!
): CreateEntity<T>, UpdateEntity<T> {
    abstract val repo: BaseRepository

    // write code to base
}

@Resource("user")
class UserRoute: APIRoute<User>() {
    val repo: UserRepository = UserRepository()
    //...
}
```

1. You can create a parent argument to define the base path for your API.

## Define route

```kotlin
fun Application.module() {
    install(Resources)

    createRoutes<UserRoute, User>() // (1)!
}
```

1.  Also handles some basic functionality with serialization using kotlinx

## URL Pattern

`createRoutes` creates the following URL patterns for your entity based on the interfaces implemented.
Any prefix url can be added by adding a parent to your route!

<style>
.tg  {
    border: .05rem solid var(--md-typeset-table-color);
    border-collapse:collapse;
    border-spacing:0;
}
.tg td {
    border: .05rem solid var(--md-typeset-table-color);
    border-style:solid;
    overflow:hidden;
    padding:10px 5px;
    word-break:normal;
}
.tg-0lax{
    text-align: center !important;
    vertical-align: middle !important;
}
</style>
<table class="tg" style="width: 100% !important;"><thead>
  <tr>
    <th class="tg-0lax">URL</th>
    <th class="tg-0lax">Method</th>
    <th class="tg-0lax">Interface<br></th>
  </tr></thead>
<tbody>
  <tr>
    <td class="tg-0lax" rowspan="2">{entity}/<br></td>
    <td class="tg-0lax">GET</td>
    <td class="tg-0lax">GetEntity</td>
  </tr>
  <tr>
    <td class="tg-0lax">POST</td>
    <td class="tg-0lax">CreateEntity<br></td>
  </tr>
  <tr>
    <td class="tg-0lax" rowspan="3">{entity}/{pk}/</td>
    <td class="tg-0lax">GET</td>
    <td class="tg-0lax">GetEntity<br></td>
  </tr>
  <tr>
    <td class="tg-0lax">PUT</td>
    <td class="tg-0lax">UpdateEntity</td>
  </tr>
  <tr>
    <td class="tg-0lax">DELETE</td>
    <td class="tg-0lax">DeleteEntity</td>
  </tr>
  <tr>
    <td class="tg-0lax">{entity}/{action}/</td>
    <td class="tg-0lax">POST</td>
    <td class="tg-0lax">EntityAction</td>
  </tr>
</tbody>
</table>