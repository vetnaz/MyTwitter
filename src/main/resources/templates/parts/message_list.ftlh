<#include "security.ftlh">

<div class="card-columns">
    <#list messages as message>
        <div class="card mt-2">
            <div class="card-img-top">
                <#if message.filename??>
                    <img src="/img/${message.filename}" alt="${message.filename}" class="card-img-top">
                </#if>
            </div>
            <div class="card-body">
                <h3> ${message.id}</h3>
                <p>${message.text}</p>
                <p>${message.tag}</p>
            </div>
            <div class="card-footer">
                <strong><a href="/user-messages/${message.author.id}">${message.authorName}</a></strong>
                <#if message.author.id == currentUserId>
                <strong><a class="btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">Edit</a></strong>
                </#if>
            </div>
        </div>
    <#else>Немає повідомлень
    </#list>
</div>