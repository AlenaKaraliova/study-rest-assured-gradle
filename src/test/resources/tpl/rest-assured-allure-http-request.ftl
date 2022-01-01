<p><b>URL</b></p>
<p><#if data.method??>${data.method}<#else>GET</#if>: <#if data.url??>${data.url}<#else>Unknown</#if></p>

<#if (data.headers)?has_content>
<p><b>Headers</b></p>
<#list data.headers as name, value>
    <p>${name}: ${value}</br></p>
</#list>
</#if>

<p><b>Body</b></p>
<#if data.body??>${data.body}<#else>No body</#if>

<#if data.curl??>
<p><b>Curl</b></p>
<p>${data.curl}</p>
</#if>