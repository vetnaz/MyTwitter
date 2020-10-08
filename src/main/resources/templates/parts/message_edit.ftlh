<a class="btn btn-primary mt-3 mb-2" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Add new message
</a>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group">
        <form  method="post" enctype="multipart/form-data">
            <input type="text" class="form-control mt-3 ${(textError??)?string('is-invalid','')}" name="text" value="<#if message??>${message.text}</#if>" placeholder="Введіть повідомдення">
            <#if textError??>
                <div  class="invalid-feedback">
                    ${textError}
                </div>
            </#if>
            <input type="text" class="form-control mt-3 ${(tagError??)?string('is-invalid','')}" value="<#if message??>${message.tag}</#if>" name="tag" placeholder="Тег">
            <#if tagError??>
                <div  class="invalid-feedback">
                    ${tagError}
                </div>
            </#if>
            <div class="custom-file mt-3">
                <input type="file" class="custom-file-input" name="file">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
            <button type="submit" class="btn btn-primary mt-3">Додати</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>" />
        </form>
    </div>
</div>