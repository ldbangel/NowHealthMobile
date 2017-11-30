<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String shareFrequency=request.getParameter("sharenumber")==null?"":request.getParameter("sharenumber"); //获取分享次数
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="x-rim-auto-match" content="none">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="keywords" content="晶算师保险,特价保险,互联网保险,少儿保险,儿童保险,意外伤害保险,个人重大疾病保险,健康保险,意外保险,交通意外险">
    <meta name="description" content="晶算师是国内创新型互联网保险服务平台，是特惠保险的先行者，晶算师聚焦于意外险和健康疾病险等纯保障型产品，将保险回归保障本源，并以一种简单而健康的方式呈现给消费者。">
    <title>注册信息</title>
    <link rel="stylesheet" href="<%=path%>/views/css/personInfors.css">
    <link rel="stylesheet" href="<%=path%>/views/css/mobiscroll_date.css">
    <link rel="stylesheet" href="<%=path%>/views/css/optiscroll.css">
    <script type="text/javascript">
         var shareFrequency ="<%=shareFrequency%>";
         if(shareFrequency==null||shareFrequency==""){
    	      shareFrequency=0;
          }
    </script>
</head>
<body>
<form id="form" action="<%=path%>/personController/goTopayinfor.do" method="post">
<div class="content">
    <div class="main">
    <div style='background:#f1f1f1;line-height:45px; color:#333;font-size:0.8rem;'><img src="/NowHealthMobile/views/images/basic-info.png"style="width:19px;vertical-align:middle;margin-right: 15px;"><span>基本信息 </span></div>
        <div class="header_box">
            <div class="info"><p style="font-size:1rem;">有效期 <span style="display:inline-block;width:53%;text-align:right;font-size: 15px;">一 年</span></p></div>
            <div class="date_box">
                <span>生效日期</span>
                <input type="text" value="" id="date" readonly="readonly" name="effectivedate">
            </div>
        </div>
        <div class="item_tile"><img src="/NowHealthMobile/views/images/man-info.png"style="width:20px;vertical-align:middle;margin-right: 15px;padding-bottom: 8px;"><span>用户信息</span></div>
        <div>
            <div class="list_con">
                <div><span>姓名</span><input type="text"placeholder="请填写用户姓名" id="purchasername" name="purchasername" maxlength="4" style="width: 72%;"></div>
                <div class="ID-box"><span>证件类型</span>
                    <input type="text" value="身份证" id="ID_ipt" readonly="readonly">
                    <select name="cardStyle" id="ID" value="">
			            <option  value="身份证">身份证</option>
			            <option  value="护照">护照</option>
                    </select>
                    <b></b>
                </div>
                <ul class="passport-box">
			  	  <li><span>护照号</span><input id="passport" name="passportID" type="text" placeholder="请填写用户护照号" maxlength="9"/></li>
			  	  <li><span>性别</span>
			  	      <input id= "man"   name="purchaserSex" type="radio" value="1" checked="checked">男&nbsp;&nbsp;&nbsp;
			          <input id= "women" name="purchaserSex" type="radio" value="0" >女
			      </li>
			  	 <li><span>年龄</span><input id="age" name="purchaserAge" type="text" style="width: 72%;"placeholder="请填写用户年龄"maxlength="2" /></li>
                </ul>
                <div class="person-ID"><span>身份证号</span><input style="width: 72%;"type="text"placeholder="请填写用户证件号码" id="purchaserid" name="purchaserID" maxlength="18" onblur="setUpperCase(this);" onkeyup="if(value!=value.replace(/[\W]/g,''))value=value.replace(/[\W]/g,'')"></div>
                <div class="CM"><span>身高</span><input id="high"   name="purchaserHigh"   type="text" style="width: 72%;"placeholder="请填写用户身高"maxlength="3" /></div>
			    <div class="KG"><span>体重</span><input id="weight" name="purchaserweight" type="text" style="width: 72%;"placeholder="请填写用户体重"maxlength="3" /></div>
			    <div>
			         <div class="Alg-list">
			            <span class="isalg" style="margin:1px">是否有过敏史&nbsp;&nbsp;&nbsp; </span>
			            <input id= "isallergy" name="isAllergy" type="radio" value="1" >&nbsp;是&nbsp;&nbsp;&nbsp;
			            <input id= "noallergy" name="isAllergy" type="radio" value="0" checked="checked" >&nbsp;否
			         </div>
			    </div>
			    <div>
			         <div class="Alg-list">
			            <span class="isalg">是否有家族病史</span>
			            <input id= "isfamilyillness" name="isfamilyillness" type="radio" value="1" >&nbsp;是&nbsp;&nbsp;&nbsp;
			            <input id= "isfamilyillness" name="isfamilyillness" type="radio" value="0" checked="checked" >&nbsp;否
			         </div>
			    </div>
                <div><span>邮箱</span><input style="width: 72%;"type="text"  placeholder="请填写用户邮箱号" id="purchaseremail" name="purchaseremail" onblur="Trim(this);"></div>
                <div><span>手机号</span><input style="width: 72%;"type="text"placeholder="请填写用户手机号" id="purchaserphoneno" name="purchaserphoneno" maxlength="11" onKeyUp="if(value!=value.replace(/[^\d]/g,''))value=value.replace(/[^\d]/g,'')"></div>
                <input type="text" style="display:none" name="orderno" value="${baseInfor.orderno}"/>
            </div>
            <div class="smscode_list">
                <span style="font-size:1rem;">验证码</span>
                <input type="text" placeholder="请填写验证码" maxlength="6" id="validationNo">
                <p onclick="getCheckCode();">获取验证码</p>
                <!-- <span >获取验证码</span> -->
            </div>
        </div>
       <!--  <div class="item_tile">被保人信息</div> -->
        <div>
            <ul class="list_con" style="border-top:1px solid #ddd;">
                <!-- <li>
                    <span>给谁投保</span>
                    <select name="linkRelationship">
                        <option value="0">本人</option>
                        <option value="1">其他人</option>
                    </select>
                </li> -->
                <li><span>服务费</span><strong>&yen;</strong><input type="text" readonly="readonly" value="${baseInfor.totalamount}" id="pay_money" name="totalamount"></li>
            </ul>
        </div>
        <div class="insure_article"><span class="check-icon"><input type="checkbox" id="agree" ></span>我已仔细阅读并同意 <span class="Dr-artinfo">《「Dr.康」服务协议》</span></div>
    </div>
    <a href="javascript:submitForm()"><div class="pay_box" >确认订单<span></span>元</div></a>
    <!-- <a href="javascript:completehuidiao()"><div class="pay_box">回调测试<span></span>元</div></a> -->
   <div class="select-layer-box">
       <div  class="sel-ID-list">
           <p><span class="yes">确定</span></p>
       <ul id="sel-ID-list">
	       <li class="FFF">身份证<i></i></li>
	       <li>护照<i></i></li>
       </ul>
       </div>
   </div>
    <div class="shade-layer" id="shade-layer">
       <div class="agreement-box anima" id="agreement-box">
            <div class="header">「Dr.康」微信公众号服务协议<i class="close-icon"><img src="<%=path%>/views/images/close.png"></i></div>
            <div class="detail-list">
                    <div id="os1" class="optiscroll column mid-50">
                     <p style="text-align:center;">
            <strong>
               <span style="font-family: 黑体;font-size: 21px">「Dr.康」微信公众号服务协议</span>
            </strong>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">
                             欢迎您使用「Dr.康」微信公众号及服务！为使用「Dr.康」微信公众号及服务，您应当阅读并遵守《「Dr.康」微信公众号服务协议》请您务必审慎阅读、充分理解各条款内容，并选择接受或不接受。
                             除非您已阅读并接受本协议所有条款，否则您无权使用「Dr.康」相关服务。您的使用行为即视为您已阅读并同意上述协议的约束。如您不同意本协议，请勿使用「Dr.康」微信公众号。如果您未满18周岁，
                             请在法定监护人的陪同下阅读本协议。 如有任何疑问可拨打「Dr.康」关爱中心热线电话400-657-1911向工作人员咨询，请您对本服务完全理解后再选择同意本服务协议。
            </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">1. 协议的范围 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">1.1 协议适用主体范围  </span>
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">本协议是您与时康国际和丁香园之间关于您使用本公众号，以及使用「Dr.康」相关服务所订立的协议。</span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">1.2 本协议指向内容  </span>
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">本协议项下的许可内容是指丁香园向用户提供的包括但不限于「Dr.康」微信公众号。</span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">1.3 协议关系及冲突条款  </span>
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">本协议内容同时包括时康国际和丁香园可能不断发布的关于本服务的相关协议、业务规则等内容。上述内容一经正式发布，即为本协议不可分割的组成部分，您同样应当遵守。</span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">2. 关于本服务  </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">2.1 本服务的内容   </span>
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">在遵守本协议条款和条件的情况下，您获得在「Dr.康」微信公众号上有限的、个人的、不可转让及非排他性的许可，以使用本公众号。</span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">2.2 本服务许可的范围  </span>
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">本协议是您与时康国际和丁香园之间关于您使用本公众号，以及使用「Dr.康」相关服务所订立的协议。</span>
            </p>

            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">2.3本条及本协议其他条款未明示授权的其他一切权利仍由时康国际和丁香园保留，您在行使这些权利时须另外取得时康国际和丁香园的书面许可。时康国际和丁香园如果未行使前述任何权利，并不构成对该权利的放弃。</span>
            </p>

            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">3. 您的职责与受益   </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">3.1您的职责   </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
             <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">3.1.1提交基本信息：准确真实填报您的个人信息。</span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">3.2您的受益：  </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">3.2.1线上团队   </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">3.2.1.1在线问诊---口袋里的家庭医生随时为您服务，及时回复你的健康咨询； </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">3.2.1.2个性化的健康资讯---定制您专属的健康话题，为您提供前沿健康资讯；  </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">3.2.1.3治未病---丁香园专业医疗团队为您提供专属健康提升方案，让疾病“防患于未然”。  </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">3.2.1.4关爱专员---一周五天，每天八小时的健康热线（法定节假日除外），适时主动给您健康指导。 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">3.2.2线下团队  </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px"> 线下VIP专家诊疗---「Dr.康」推荐线下医院便捷就医, 线下预约服务仅适用于中国大陆公立医院特需/国际部和私立医疗机构。 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">4. 「Dr.康」微信服务的获取   </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">4.1 您可以通过线上或线下方式购买使用「Dr.康」微信服务。  </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">4.2 如果您从未经时康国际及丁香园共同授权的第三方获取「Dr.康」微信服务，时康国际和丁香园无法保证该「Dr.康」微信服务能够正常使用，并对因此给您造成的损失不予负责。  </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">5. 「Dr.康」微信公众号的更新   </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">5.1 为了改善用户体验、完善服务内容，时康国际和丁香园将不断努力开发新的服务，并为您不时提供更新（这些更新可能会采取修改、功能强化、版本升级等形式）。</span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">5.2 为了保证本公众号及服务的安全性和功能的一致性，时康国际和丁香园有权不经向您特别通知而对公众号进行更新，或者对公众号的部分功能效果进行改变或限制。 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">6. 用户个人信息保护   </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">6.1 保护用户个人信息是时康国际和丁香园的一项基本原则，时康国际和丁香园将会采取合理的措施保护用户的个人信息。除法律法规规定的情形外，未经用户许可丁香园不会向第三方公开、透露用户个人信息。时康国际和丁香园对相关信息采用专业加密存储与传输方式，保障用户个人信息的安全。</span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">6.2 您在使用本服务的过程中，需要提供一些必要的信息。若国家法律法规或政策有特殊规定的，您需要提供真实的身份信息。若您提供的信息不完整，则无法使用本服务或在使用过程中受到限制。 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">6.3 时康国际和丁香园将运用各种安全技术和程序建立完善的管理制度来保护您的个人信息，以免遭受未经授权的访问、使用或披露。 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">6.4时康国际和丁香园不会向时康国际和丁香园以外的任何公司、组织和个人披露您的个人信息，但经过您同意或法律法规另有规定以及本协议约定的情形除外。 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">6.5个人信息及记录的收集、使用和保密</span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">6.5.1收集和处理个人信息  </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">本服务将收集您自愿提交给我们的信息，如您的姓名、电话号码、地址、年龄、身高、体重、疾病史、用药和就诊相关信息(统称，“您的个人信息”)。「Dr.康」服务提供方将依照您的个人信息来联系您以提供健康方案及定期的指导和关爱。组织方也会依据您的个人信息为您提供健康教育信息。 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">6.5.2删除权及选择退出权 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">您有权选择将为上述目的收集的信息从「Dr.康」服务提供方的数据库中删除。您也可以在服务期间的任何时候选择退出。选择退出后，活动组织人员将不再提供健康咨询服务。 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">6.5.3信息保密  </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">您参加「Dr.康」服务过程中的个人资料将严格保密。所有项目相关人员都被要求对您的身份信息和问诊记录保密，可以识别您身份的信息将不会透露给非相关人员，除非获得您的许可。在必要情况下，只有政府管理部门可以查阅您的这些信息。</span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">7. 参与、拒绝、终止服务的说明  </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">是否参加服务完全取决于您的意愿。如果您对本服务有任何疑问，您可以拨打「Dr.康」关爱中心热线400-657-1911提出。</span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">您有权拒绝参加此次服务。</span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">8. 联系方式</span>
            </p>
             <p style="text-indent:37px;line-height:150%">
             <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">在服务过程中，如果您有任何与本次服务有关的疑问或困惑，请联系「Dr.康」关爱中心：</span>
             <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">电话号码：400-657-1911（现阶段工作时间段: 周一至周五9:00-18:00, 周末和法定节假日除外）</span>
             <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">如果在服务过程中有任何重要的新信息，可能影响您继续享受此服务时，「Dr.康」服务人员将会及时通知您。</span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">9. 主权利义务条款  </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">9.1 用户注意事项  </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">9.1.1 您理解并同意：为了向您提供有效的服务，本公众号使用过程中可能产生数据流量的费用，用户需自行向运营商了解相关资费信息，并自行承担相关费用。</span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">9.1.2 您理解并同意时康国际和丁香园将会尽其努力保障您在本公众号及服务中的数据存储安全，但是，时康国际和丁香园并不能就此提供完全保证，包括但不限于以下情形：</span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">9.1.2.1 时康国际和丁香园不对您在本公众号及服务中相关数据的删除或储存失败负责；</span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">9.1.2.2 时康国际和丁香园有权根据实际情况自行决定单个用户在本公众号及服务中数据的最长储存期限，并在服务器上为其分配数据最大存储空间等。您可根据自己的需要自行备份本公众号及服务中的相关数据；</span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">9.1.2.3 如果您停止使用本公众号及服务或服务被终止或取消，时康国际和丁香园可以从服务器上永久地删除您的数据。服务停止、终止或取消后，时康国际和丁香园没有义务向您返还任何数据。</span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">9.1.3 用户在使用本公众号及服务时，须自行承担如下来自时康国际和丁香园不可掌控的风险内容，包括但不限于：</span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">9.1.3.1 由于不可抗拒因素可能引起的个人信息丢失、泄漏等风险；</span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">9.1.3.2 用户在通过本公众号使用第三方服务时，因第三方服务及相关内容所可能导致的风险，由用户自行承担；</span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">9.1.3.3 由于无线网络信号不稳定、无线网络带宽小等原因（同上），所引起的登录失败、资料同步不完整、页面打开速度慢等风险。 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">10. 用户行为规范   </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.1 「Dr.康」微信公众号使用规范  </span>
             <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px"> 除非法律允许或时康国际和丁香园的书面许可，您使用「Dr.康」服务的过程中不得从事下列行为： </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.1.1 删除本公众号及其副本上关于著作权的信息；  </span>
            </p>
              <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.1.2 对本公众号进行反向工程、反向汇编、反向编译，或者以其他方式尝试发现本公众号的源代码； </span>
            </p>
              <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.1.3 对时康国际和丁香园拥有知识产权的内容进行使用、出租、出借、复制、修改、链接、转载、汇编、发表、出版、建立镜像站点等；</span>
            </p>
              <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.1.4 对本公众号或者本公众号运行过程中释放到任何终端内存中的数据、公众号运行过程中客户端与服务器端的交互数据，以及本公众号运行所必需的系统数据，进行复制、修改、增加、删除、挂接运行或创作任何衍生作品，形式包括但不限于使用插件、外挂或非时康国际和丁香园经授权的第三方工具/服务接入本公众号和相关系统；</span>
            </p>
              <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.1.5 通过修改或伪造公众号运行中的指令、数据，增加、删减、变动公众号的功能或运行效果，或者将用于上述用途的公众号、方法进行运营或向公众传播，无论这些行为是否为商业目的； </span>
            </p>
              <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.1.6 通过非时康国际和丁香园开发、授权的第三方公众号、插件、外挂、系统，登录或使用丁香园时康国际公众号及服务，或制作、发布、传播上述工具； </span>
            </p>
              <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.1.7 自行或者授权他人、第三方公众号对本公众号及其组件、模块、数据进行干扰； </span>
            </p>
              <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.1.8 其他未经时康国际和丁香园明示授权的行为。 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.1.9 其他违反法律法规、政策的行为。 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.2 对自己行为负责。您充分了解并同意，您必须为自己的一切行为负责。您应对本服务中的内容自行加以判断，并承担因使用内容而引起的所有风险，包括因对内容的正确性、完整性或实用性的依赖而产生的风险。时康国际和丁香园无法且不会对因前述风险而导致的任何损失或损害承担责任。 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.3 违约处理  </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.3.1 您理解并同意，时康国际和丁香园有权依合理判断对违反有关法律法规或本协议规定的行为进行处理，对违法违规的任何用户采取适当的法律行动，并依据法律法规保存有关信息向有关部门报告等，用户应独自承担由此而产生的一切法律责任。 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">10.3.2 您理解并同意，因您违反本协议或相关服务条款的规定，导致或产生第三方主张的任何索赔、要求或损失，您应当独立承担责任；时康国际和丁香园因此遭受损失的，您也应当一并赔偿。 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">11. 知识产权声明  </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">11.1 时康国际和丁香园是本公众号的知识产权权利人。「Dr.康」商标权归时康国际所有，其余关于本公众号的一切著作权、专利权、商业秘密等知识产权，以及与本公众号相关的所有信息内容（包括但不限于文字、图片、音频、视频、图表、界面设计、版面框架、有关数据或电子文档等）均受中华人民共和国法律法规和相应的国际条约保护，时康国际和丁香园共同享有上述知识产权，但相关权利人依照法律规定应享有的权利除外。 </span>
            </p>
            <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">11.2 未经时康国际和丁香园或相关权利人书面同意，您不得为任何商业或非商业目的自行或许可任何第三方实施、利用、转让上述知识产权。 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">12. 终端安全责任 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">12.1 您理解并同意，本公众号同大多数互联网公众号一样，可能会受多种因素影响，包括但不限于用户原因、网络服务质量、社会环境等；也可能会受各种安全问题的侵扰，包括但不限于他人非法利用用户资料，进行现实中的骚扰；因此，您应加强信息安全及个人信息的保护意识，以免遭受损失。 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">12.2 您不得制作、发布、使用、传播用于窃取他人个人信息、财产的恶意程序。 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">12.3 维护公众号安全与正常使用是时康国际和丁香园和您的共同责任，丁香园将按照行业标准合理审慎地采取必要技术措施保护您的终端设备信息和数据安全，但是您承认和同意时康国际和丁香园并不能就此提供完全保证。 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px;font-weight: 600">13. 其他  </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">13.1您使用本公众号即视为您已阅读并同意受本协议的约束。时康国际和丁香园有权在必要时修改本协议条款。您可以在本公众号的最新版本中查阅相关协议条款。本协议条款变更后，如果您继续使用本公众号，即视为您已接受修改后的协议。 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">13.2 本协议签订地为中华人民共和国上海市。 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">13.3 本协议的成立、生效、履行、解释及纠纷解决，适用中华人民共和国大陆地区法律（不包括冲突法）。 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">13.4 若您和时康国际及丁香园之间发生任何纠纷或争议，首先应友好协商解决；协商不成的，您同意将纠纷或争议提交本协议签订地有管辖权的人民法院管辖。 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">13.5 本协议所有条款的标题仅为阅读方便，本身并无实际涵义，不能作为本协议涵义解释的依据。 </span>
            </p>
             <p style="text-indent:37px;line-height:150%">
            <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">13.6 本协议条款无论因何种原因部分无效或不可执行，其余条款仍有效，对双方具有约束力。 </span>
             <span style=";font-family:仿宋_GB2312;line-height:150%;font-size:17px">我已经阅读了上述有关「Dr.康」服务的介绍，而且有机会就此项服务与服务提供方讨论并提出问题。我知晓享受此服务是自愿的，我确认已有充足时间对此进行考虑，最后，我决定同意使用「Dr.康」服务。 </span>
            </p>
                    </div>
            </div>
       </div>
    </div>
</div>
</form>
<!--错误信息提示框  -->
<div id="errorhei" class="errorhei" style="z-index: 100; display:none;position: fixed; width: 100%;height: 100%; background: rgba(0,0,0,0.5);top: 0;">
    <div style="   position: fixed;top: 35%; width:100%;height:auto;text-align:center;">
        <div style="width:85%;margin:0 auto;max-width:300px;background:#fff;color: #333; border-radius:5px;padding: 20px 0px 3px 0px;box-shadow: 0 0 20px 2px #333;">
            <h2 style="font-size: 17px;color: #222;color: #aeaeae;padding-bottom:5px;">提示信息</h2>
            <p style="margin-bottom: 10px;font-size: 12px;padding: 15px 20px;text-align: center; color:#444;" id="Message"></p >
            <div style="border-top:1px solid #ddd;font-size:17px;color:#333;padding: 8px 0px;"><span style="display:block;width:100%;font-size:15px" id="ensure">确定</span></div>
        </div>
    </div>
</div>
<!--页面加载框  -->
<div style="position:fixed;top:0px;left:0px;width:100%;height:100%;z-index: 100;display:none;"id="pop">
<div style="position: fixed;top:35%; width:100%;height:auto;text-align:center;">
     <span style='display:inline-block;height: auto;border-radius: 5px;background: rgba(0,0,0,0.6);color: #fff;font-size: 10px;letter-spacing: 2px; padding: 20px;'>
        <img  src="<%=path%>/views/images/mu_loading.gif" style='width: 36px; margin-bottom: 5px;'>
        <p id="prompt"></p>
	</span>	 
</div>
</div>
</body>
<script type="text/javascript">
   	function getUrl() {
		return "<%=path%>";
  	}
</script>
<script src="<%=path%>/views/js/jquery-1.9.1.js"></script>
<script src="<%=path%>/views/js/mobiscroll_date.js"></script>
<script src="<%=path%>/views/js/mobiscroll.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="<%=path%>/views/js/wx_linkshare.js"></script>
<script src="<%=path %>/views/js/optiscroll.js"></script>
<script src="<%=path%>/views/js/personInfors.js"></script>
</html>