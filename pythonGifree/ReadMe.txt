# 새롭게 파일을 받았을 경우 venv를 먼저 만들고 활성화 한 뒤 requirements.txt에 있는 패캐지를 설치해야함.
# 1. 프로젝트 디렉토리로 이동. (pythonGifree로 이동하면 됨. 터미널에 표기 되어있는 마지막 위치가 \pythonGifree 면 됨.)
# 2. 가상 환경(venv) 생성 -> 터미널에서 python -m venv venv *파일 위치는 최상단 pythonGifree
# 3. 가상 환경 활성화 -> .\venv\Scripts\activate *파일 위치는 최상단 pythonGifree
# 4. requirements.txt 명시된 패키지 설치 -> pip install -r requirements.txt
# 5. 서버 키기 -> app.py 파일이 있는 곳으로 이동 후 명령어 입력 "cd fastapi" -> uvicorn app:app --host 0.0.0.0 --port 8000
# 5.1 주의점: app.py 파일이 있는 곳으로 이동했을 때 가상환경이 활성화 되어있지 않으면 활성화 시키고 해야함.

# 가상환경 설정 및 서버 실행
# ..\venv\Scripts\activate
# uvicorn app:app --host 0.0.0.0 --port 8000

# Python 환경에 설치된 모든 패키지와 그 버전을 저장할 경우
# 가상환경이 켜진 상태에서 requirments.txt가 있는 파일 위치에서 진행할 것.(최상단 폴더 위치에서 할 것.)
# pip freeze > requirements.txt


# cmd 터미널로 서버를 실행하려고 하는데 venv가 켜져있어 종료해야 하는 경우
# pythonGifree 파일로 이동 "cd pythonGifree"
# venv 실행(이미 실행되고 있어도 재실행) !!!!!!pythonGifree 위치에서 해야함.!!!!!!!
# 터미널에 "deactivate" 입력하면 (venv)가 사라짐.  !!!!!!pythonGifree 위치에서 해야함.!!!!!!!