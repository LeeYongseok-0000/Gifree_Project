import matplotlib
import matplotlib.font_manager as fm
import os

print("Matplotlib 폰트 캐시 삭제를 시도합니다...")

try:
    # 캐시 디렉토리 경로를 찾는 두 가지 방법을 모두 시도합니다.
    cache_dir = ''
    try:
        # 방법 1 (최신 버전)
        cache_dir = fm.get_cachedir()
    except AttributeError:
        # 방법 2 (이전 버전 또는 다른 경로)
        print("fm.get_cachedir()를 찾을 수 없어, 다른 방법을 시도합니다.")
        cache_dir = matplotlib.get_cachedir()
    
    if not os.path.exists(cache_dir):
        raise FileNotFoundError("Matplotlib 캐시 디렉토리를 찾을 수 없습니다.")

    print(f"발견된 캐시 디렉토리: {cache_dir}")

    # 캐시 디렉토리 내의 폰트 관련 캐시 파일 삭제
    deleted_count = 0
    for filename in os.listdir(cache_dir):
        if filename.lower().startswith('fontlist') or filename.lower().endswith(('.cache', '.json')):
            file_path = os.path.join(cache_dir, filename)
            try:
                os.remove(file_path)
                print(f"- {filename} 삭제 완료.")
                deleted_count += 1
            except Exception as e:
                print(f"- {filename} 삭제 실패: {e}")
    
    if deleted_count > 0:
        print(f"✅ 총 {deleted_count}개의 폰트 캐시 파일을 삭제했습니다.")
    else:
        print("ℹ️ 삭제할 폰트 캐시 파일이 없습니다. 이미 깨끗한 상태일 수 있습니다.")
    
    print("이제 FastAPI 서버를 다시 시작하면, 캐시가 새로 만들어지면서 한글 폰트가 적용됩니다.")

except Exception as e:
    print(f"❌ 폰트 캐시 처리 중 예상치 못한 오류 발생: {e}")
    import traceback
    traceback.print_exc()