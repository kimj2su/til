# git log
- commit 된 이력을 조회하는 명령어
- 조건식을 활용하여 원하는 정보만 골라서 볼 수 있음

# git branch
```bash
git branch <branch name> # branch 생성
git checkout <branch name> # branch 이동

git checkout -b <branch name> # branch 생성 -b 옵션을 주면서 생성하면 생성과 동시에 checkout
git commit -m "commit message" # commit
git checkout main
```

## git Fast-Forward Merge
- branch 작업 후 main branch에 병합시 main에 변경이 없는 상태인 경우 Fast-Forward Merge가 발생
- 깃은 변경이력에 대해서 타겟이 되는 브랜치에와서 명령어를 실행해야 한다.
```bash
git checkout main
git merge <branch name>
```

# 3-way Merge
- main branch에 변경이 있고, 병합할 branch에도 변경이 있는 경우
- 가장 많이 만나는 상황이다.
- merge commit이 발생한다.

```bash
git checkout -b <branch name>
git commit -m "commit message"
git checkout main # main애 변경이 있고, <branch name>에도 변경이 있는 경우
git merge <branch name>

```

# 3-way Merge Conflict
- branch 작업 후 병합시 Conflict(충돌)이 발생하는 경우
1. main 상태에서 branch 생성
2. bracnh에서 작업 후 commit
3. main에서 작업 후 commit
4. main에서 branch 병합
5. 충돌 발생

```bash
git status # 충돌이 발생한 파일 확인

fix conflict and run "git commit" # 충돌이 발생한 파일을 수정 후 commit
use "git merge --abort" to abort the merge # merge 취소
git add <file name> # 충돌이 발생한 파일을 수정
git commit -m "commit message" # commit
```

# Merge Option 사용하기
- 브랜치 작업후 메인에 병합시 두가지 형태로 진행
  - Fast-Forward Merge
  - 3-way Merge

## squash 옵션
- 브랜치 작업 후 메인에 병합시 3-way Merge를 하지 않고, 하나의 커밋으로 만들어서 병합

1. 메인상태에서 깃브랜치 명령으로 브랜치 생성
2. 브랜치에서 작업 후 커밋
3. 메인에서 git merge 명령 및 squash 옵션으로 병합

```bash
git checkout -b <branch name>
git commit -m "commit message"
git checkout main
git merge <branch name> --squash op
git commit -m "commit message"
```
   
# rebase 옵션
- 병합을 떠나서 브랜치간 병합을 하든 안 하든 브랜치에서 파생된 어떤 베이스에 대한 조건을 바꿔주는 것이다.
- merge가 아니라 rebase를 사용하면 커밋 히스토리가 깔끔하게 유지된다.
- rebase는 커밋 히스토리를 깔끔하게 유지하면서 병합(ff)하는 방법이다.

1. 메인상태에서 깃브랜치 명령으로 브랜치 생성
2. 브랜치에서 작업 후 커밋
3. 브랜치에서 리베이스를 진행
3. 메인에서 git merge 병합

```bash
# 메인에서 커밋 로그 생성
vi test.text
git add test.text
git commit -m "commit message"(main)

# rb 브랜치에서 커밋 로그 생성
git checkout -b rb
vi rb1.text
git add rb1.text
git commit -m "commit message"(rb1)

vi rb2.text
git add rb2.text
git commit -m "commit message"(rb2)

# 메인에서 커밋 로그 생성
git checkout main
vi main.text
git add main.text
git commit -m "commit message"(rb-main1)
```
위와 같은 상황에서 메인에서 머지를 하려고하면 3-way merge가 발생한다.  
그렇기 때문에 2, 3번 커밋을 1번이 아니라 마지막 커밋이 부모로 되도록 리베이스를 진행한다.  
리베이스는 메인이 아니라 바꾸고자 하는 브랜치에서 진행을 한다.

```bash
git checkout rb
git rebase main

Switched to branch 'rb'
Successfully rebased and updated refs/heads/rb.
git log
commit 0f6bab1691d52f2d1f2c291133af10668f8de44a (HEAD -> rb)
    rb2
    rb1
    rb-main1
    main
```
위와 같이 rb-main1이 rb의 부모가 되었다.
```bash
git checkout main
git merge rb
Updating 19abbfd..0f6bab1
Fast-forward
 rb1.text | 1 +
 rb2.text | 1 +
 2 files changed, 2 insertions(+)
 create mode 100644 rb1.text
 create mode 100644 rb2.text
```
위와 같이 Fast-forward merge가 발생한다.
