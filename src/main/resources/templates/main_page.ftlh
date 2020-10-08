<#import "parts/common.ftlh" as c>
<@c.page>

    <div class="form-row">
        <form class="form-inline"  method="get" action="/main" >
            <input type="text" name="text" class="form-control" placeholder="Search by tag" value="${filter?if_exists}">
            <button class="btn btn-primary ml-3" type="submit">Search</button>
        </form>
    </div>

    <#include "parts/message_edit.ftlh">

    <#include "parts/message_list.ftlh">
</@c.page>