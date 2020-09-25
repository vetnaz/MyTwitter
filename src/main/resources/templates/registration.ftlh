<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
Add new user
    <@l.login "/registration" true/>
    <div>${message?if_exists}</div>
</@c.page>