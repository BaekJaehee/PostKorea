import json
from your_model import YourModel  # 'YourModel'은 실제 사용하는 모델 클래스로 변경해야 합니다.

# 주어진 딕셔너리 데이터
region_data = {
    '서울특별시': ["강남구", "강동구", ...],
    '부산광역시': ["강서구", "금정구", ...],
    # 나머지 지역들 ...
}

# 주소 데이터를 데이터베이스에 저장하는 함수
def save_regions_to_database():
    for region1, region2_list in region_data.items():
        for region2 in region2_list:
            # 모델 객체 생성 및 저장
            model_instance = YourModel(region1=region1, region2=region2)
            model_instance.save()

# 함수 호출
save_regions_to_database()
