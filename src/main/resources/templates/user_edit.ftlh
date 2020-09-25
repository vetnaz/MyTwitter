<#import "parts/common.ftlh" as c>
<@c.page>
User editor
    <form action="/user" method="post">
        <input type="text" value="${user.username}" name="userName">
        <input type="hidden" value="${user.id}" name="userId">
        <#list roles as role>
            <div>
                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked"," ")}>${role}</label>
            </div>
        </#list>
        <button type="submit">Save</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@c.page>