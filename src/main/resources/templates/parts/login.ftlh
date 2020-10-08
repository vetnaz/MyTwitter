<#include "security.ftlh">

<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group ml-5">
            <label for="exampleInputEmail1"> User Name:  </label>
            <input type="text" class="form-control ${(usernameError??)?string('is-invalid','')} col-sm-6"
                   name="username"
                   placeholder="Type username"
                   value="<#if user??>${user.username}</#if>"
            />
            <#if usernameError??>
                <div  class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group ml-5">
            <label for="exampleInputEmail1"> Password:  </label>
            <input class="form-control ${(passwordError??)?string('is-invalid','')} col-sm-6" type="password" name="password" placeholder="Password"/>
            <#if passwordError??>
                <div  class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <#if isRegisterForm>
            <div class="form-group ml-5">
                <label for="exampleInputEmail1"> Password:  </label>
                <input class="form-control ${(password2Error??)?string('is-invalid','')} col-sm-6" type="password" name="password2" placeholder="Retype password"/>
                <#if password2Error??>
                    <div  class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
            </div>
            <div class="form-group ml-5">
                <label for="exampleInputEmail1"> Email:  </label>
                <input class="form-control ${(emailError??)?string('is-invalid','')} col-sm-6"
                       type="email"
                       name="email"
                       placeholder="some@some.com"
                       value="<#if user??>${user.email}</#if>"
                />
                <#if emailError??>
                    <div  class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
            <div>
                <div class="g-recaptcha ml-5 mt-4 mb-4" data-sitekey="6LcqDNAZAAAAAKiXAbX6SsO11n8a6Zu4Kpjxpvp7"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        </#if>
        <div>
            <button type="submit" class="btn btn-primary ml-5"><#if isRegisterForm>Create<#else >Sign In</#if></button>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegisterForm><a href="/registration" class="ml-5 mt-5">Add new user</a></#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <button type="submit" class="btn btn-primary ml-5"><#if user??>Log out<#else>Log in</#if></button>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>