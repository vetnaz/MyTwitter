<#import "parts/common.ftlh" as c>
<@c.page>
    <h5>${profileName}</h5>
    <div class="container">
        <#if !isCurrentUser>
            <#if isSubscriber>
                <a class="btn btn-info" href="/user/unsubscribe/${userChannel.id}">Unsubscribe</a>
            <#else>
                <a class="btn btn-info" href="/user/subscribe/${userChannel.id}">Subscribe</a>
            </#if>
        </#if>
        <div class="row">
            <div class="col">
                <div class="card-body">
                    <div class="card-title">Subscriptions</div>
                    <div class="card-text">
                        <a href="/user/subscribtions/${userChannel.id}/list">${subscriptionsCount}</a>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card-body">
                    <div class="card-title">Subscribers</div>
                    <div class="card-text">
                        <a href="/user/subscribers/${userChannel.id}/list">${subscribersCount}</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#if isCurrentUser>
        <#include "parts/message_edit.ftlh">
    </#if>
    <#include "parts/message_list.ftlh">
</@c.page>