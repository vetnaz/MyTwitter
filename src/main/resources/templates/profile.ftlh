<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <h5>${username}</h5>
    ${message?if_exists}
    <form method="post">
        <div class="form-group ml-5">
            <label for="exampleInputEmail1"> Password:  </label>
            <input class="form-control col-sm-6" type="password" name="password"/>
        </div>
        <div class="form-group ml-5">
            <label for="exampleInputEmail1"> Email:  </label>
            <input class="form-control col-sm-6" type="email" name="email" placeholder="some@some.com" value="${email!''}"/>
        </div>
        <div>
            <button type="submit" class="btn btn-primary ml-5">Save</button>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@c.page>