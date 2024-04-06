# ssafy_10th_9_study2
알고리즘을 까먹어 가는 사람들의 실전 감각 기르는 공간

---
# 📓 알고리즘 및 코딩 테스트 문제 풀이 공유 
알고리즘 및 코테 문제 풀이 스터디입니다.

취업하시지 않는 이상 출구는 없습니다. 

**매주 토요일 오전 10 - 12시 2문항, 날짜 별로 만들어진 폴더 내에서 진행**
(ex. 24-04-05-MockCodingTest)   

- 문제를 시간 내에 풀지 못했다면 일요일 자정 전까지 풀어서 다시 올려주시기 바랍니다.    
- 만약, 개인 사정으로 인해 참석하지 못하는 경우에도 따로 풀어서 일요일 자정 전까지 올려주시길 바랍니다. 

## ✅ 규칙
1. 금요일 자정까지 정해진 문제 제출 담당자가 2 문항을 정해 미리 공유한다.
2. **문제를 풀기 전 자신의 이름으로 된 폴더를 새로 만들어 그 안에 파일을 올리도록 한다.** (ex. Byungheon)
3. 외부 IDE 사용을 금지한다. 하단 링크 java 8 레퍼런스 참고   

      java8 - https://docs.oracle.com/javase/8/docs/api/   
      javascript - https://devdocs.programmers.co.kr/javascript/

4. commit 규칙
```
git commit -m "[PGS] {문제 이름 ex. 시험장 나누기}
```
4. 원본 저장소로 `Pull Request`를 한다. (오후에 Merge 예정)
5. 다른 사람들의 PR을 보고 자유롭게 코드리뷰를 한다.


## 🎓 초반 세팅 방법
1. 우선 해당 레포시토리를 Fork하여 개인 repository를 생성해주세요.
2. 로컬 환경에서 사용하기 위해 개인 repository를 clone해주세요. 
```
git clone [로컬 저장소 주소]
```
3. clone한 repository와 원래 original repository(organization에 있는 repo)를 연결하고 확인해주세요.
```
git remote add upstream https://github.com/ssafy-10th-9-study/algorithm.git
git remote -v
```

## 🎓 스터디 Pull-Request 방법
1. 외부 저장소(organization repository)와 동기화를 진행해준다.
```
git fetch upstream
```
2. 내가 푼 문제들을 commit 후 push를 진행한다.
3. 내 repository에 들어가서 외부 저장소로 Pull-Request를 작성한다. 
