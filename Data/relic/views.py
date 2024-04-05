from django.shortcuts import render
from .models import relic_list, relic_detail
from rest_framework.decorators import api_view
import requests
from xml.etree import ElementTree
from django.http import HttpResponse

# Create your views here.
@api_view(["GET"])
def save_list_data(request):
    # 총 개수 15323
    total_page = 154
    page_unit = 100
    # 1페이지에 100개씩

    for idx in range(1, total_page + 1):
        url = f'http://www.cha.go.kr/cha/SearchKindOpenapiList.do?&ccbaCncl=N&pageUnit={page_unit}&pageIndex={idx}'
        response = requests.get(url)

        xml_data = ElementTree.fromstring(response.content)
        items = xml_data.findall('item')

        # Result > Item > 안에 있는 데이터들 for문으로 순차적으로 저장
        for item in items:
            no = item.find('no').text
            ccma_name = item.find('ccmaName').text
            ccba_mnm1 = item.find('ccbaMnm1').text
            ccba_mnm2 = item.find('ccbaMnm2').text
            ccba_kdcd = item.find('ccbaKdcd').text
            ccba_ctcd = item.find('ccbaCtcd').text
            ccba_asno = item.find('ccbaAsno').text

            relic_list.objects.create(
                no=no,
                ccma_name=ccma_name,
                ccba_mnm1=ccba_mnm1,
                ccba_mnm2=ccba_mnm2,
                ccba_kdcd=ccba_kdcd,
                ccba_ctcd=ccba_ctcd,
                ccba_asno=ccba_asno
            )
        # print(f'{idx} page succeeded')
        print(ccma_name, ccba_mnm1, ccba_kdcd, ccba_ctcd, ccba_asno)
    return HttpResponse("saved successfully")


@api_view(["GET"])
# requests.exceptions.ConnectionError: HTTPConnectionPool(host='www.cha.go.kr', port=80): 
# Max retries exceeded with url: /cha/SearchKindOpenapiDt.do?ccbaKdcd=12&ccbaAsno=04940000&ccbaCtcd=37
# (Caused by NewConnectionError('<urllib3.connection.HTTPConnection object at 0x106065c40>:
# Failed to establish a new connection: [Errno 60] Operation timed out'))
# 이 오류 생기면서 834개에서 끊김
# requests.exceptions.ConnectionError: HTTPConnectionPool(host='www.cha.go.kr', port=80): Max retries exceeded with url: /cha/SearchKindOpenapiDt.do?ccbaKdcd=12&ccbaAsno=04940000&ccbaCtcd=37 (Caused by NewConnectionError('<urllib3.connection.HTTPConnection object at 0x106065c40>: Failed to establish a new connection: [Errno 60] Operation timed out'))
def save_detail_data(request):
    # 필수 파라미터 ccbaKdcd, ccbaAsno, ccbaCtcd
    for relic_item in relic_list.objects.all():
        kdcd = relic_item.ccba_kdcd
        asno = relic_item.ccba_asno
        ctcd = relic_item.ccba_ctcd

        url = f'http://www.cha.go.kr/cha/SearchKindOpenapiDt.do?ccbaKdcd={kdcd}&ccbaAsno={asno}&ccbaCtcd={ctcd}'
        response = requests.get(url)

        xml_data = ElementTree.fromstring(response.content)

        # result 바로 아래에 있는 것들은 find('result') 할 필요 없음
        ccba_cpno_element = xml_data.find('ccbaCpno')
        ccba_cpno = ccba_cpno_element.text.strip() if ccba_cpno_element is not None and ccba_cpno_element.text is not None else None

        # Null이면 0
        longitude = xml_data.find('longitude').text if xml_data.find('longitude') is not None else None
        latitude = xml_data.find('latitude').text if xml_data.find('latitude') is not None else None

        # item은 해줘야함 result > item > ...
        item = xml_data.find('item')
        
        ccma_name_element = item.find('ccmaName')
        ccma_name = ccma_name_element.text.strip() if ccma_name_element is not None and ccma_name_element.text is not None else None
        ccba_mnm1 = relic_item.ccba_mnm1
        ccba_mnm2 = relic_item.ccba_mnm2
        gcode_name_element = item.find('gcodeName')
        gcode_name = gcode_name_element.text.strip() if gcode_name_element is not None and gcode_name_element.text is not None else None
        bcode_name_element = item.find('bcodeName')
        bcode_name = bcode_name_element.text.strip() if bcode_name_element is not None and bcode_name_element.text is not None else None
        mcode_name_element = item.find('mcodeName')
        mcode_name = mcode_name_element.text.strip() if mcode_name_element is not None and mcode_name_element.text is not None else None
        scode_name_element = item.find('scodeName')
        scode_name = scode_name_element.text.strip() if scode_name_element is not None and scode_name_element.text is not None else None
        ccba_quan_element = item.find('ccbaQuan')
        ccba_quan = ccba_quan_element.text.strip() if ccba_quan_element is not None and ccba_quan_element.text is not None else None
        ccba_asdt_element = item.find('ccbaAsdt')
        ccba_asdt = ccba_asdt_element.text.strip() if ccba_asdt_element is not None and ccba_asdt_element.text is not None else None
        ccba_lcad_element = item.find('ccbaLcad')
        ccba_lcad = ccba_lcad_element.text.strip() if ccba_lcad_element is not None and ccba_lcad_element.text is not None else None
        # Null인게 있어서 strip()이 안먹혀서 오류 발생하는 경우 있음
        ccce_name_element = item.find('ccceName')
        ccce_name = ccce_name_element.text.strip() if ccce_name_element is not None and ccce_name_element.text is not None else None
        ccba_poss_element = item.find('ccbaPoss')
        ccba_poss = ccba_poss_element.text.strip() if ccba_poss_element is not None and ccba_poss_element.text is not None else None
        ccba_admin_element = item.find('ccbaAdmin')
        ccba_admin = ccba_admin_element.text.strip() if ccba_admin_element is not None and ccba_admin_element.text is not None else None
        image_url_element = item.find('imageUrl')
        image_url = image_url_element.text.strip() if image_url_element is not None and image_url_element.text is not None else None
        content_element = item.find('content')
        content = content_element.text.strip() if content_element is not None and content_element.text is not None else None

        try:
            relic_list_instance = relic_list.objects.get(ccba_kdcd=kdcd, ccba_asno=asno, ccba_ctcd=ctcd)
            relic_detail.objects.create(
                list_id=relic_list_instance,
                ccba_kdcd=kdcd,
                ccba_asno=asno,
                ccba_ctcd=ctcd,
                ccba_cpno=ccba_cpno,
                longitude=longitude,
                latitude=latitude,
                ccma_name=ccma_name,
                ccba_mnm1=ccba_mnm1,
                ccba_mnm2=ccba_mnm2,
                gcode_name=gcode_name,
                bcode_name=bcode_name,
                mcode_name=mcode_name,
                scode_name=scode_name,
                ccba_quan=ccba_quan,
                ccba_asdt=ccba_asdt,
                ccba_lcad=ccba_lcad,
                ccce_name=ccce_name,
                ccba_poss=ccba_poss,
                ccba_admin=ccba_admin,
                image_url=image_url,
                content=content
            )
        except relic_list.DoesNotExist:
            return "No Data"


@api_view(["GET"])
def save_regions(request):
    for detail in relic_detail.objects.all():
        address = detail.ccba_lcad
    
        if address is not None and address != '' and address != '기타 전국일원':
            address_parts = address.split()
            if len(address_parts) >= 2:
                region1 = address_parts[0]
                region2 = address_parts[1]
            
                detail.region1 = region1
                detail.region2 = region2
                detail.save()
            else:
                detail.region1 = address_parts[0]
                detail.save()
        elif address == '기타 전국일원':
            region1 = address
            detail.region1 = region1
            detail.save()

    return HttpResponse("Regions updated successfully.")