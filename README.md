
# 뉴스피드 API 명세서

이 문서는 뉴스피드 프로젝트의 API 명세서를 제공합니다. 이 프로젝트는 사용자가 게시물을 작성하고, 댓글 및 좋아요를 통해 소통하며, 다른 사용자를 팔로우할 수 있는 소셜 미디어 플랫폼입니다.

---

## API 엔드포인트

### 1. 사용자 관리
#### **1.1 사용자 생성**
**POST** `/users`
```json
{
    "email": "string",
    "password": "string",
    "reterPassword": "string",
    "userName": "string"
}
```
- **응답**: `201 Created`
- 
#### **1.2 로그인**
**POST** `/users/login`
```json
{
    "email": "String",
    "password": "String"
}
```
- **응답**: '200 Ok'

- #### **1.3 로그아웃**
**POST** `/users/logout`
- **응답**: '200 Ok'

#### **1.2 회원 정보 수정**
**POST** `/users/{userId}`
```json
{
    "password": "String",
    "reterPassword": "String",
    "userName": "String"
}
```
- **응답**: '200 Ok'

- 
#### **1.2 사용자 프로필 조회**
**GET** `/users/profile/{user_id}`
- **응답**:
```json
{
    "userName": "String",
    "introduction": "String",
    "followingNum": "Long",
    "introduction": "Long",
    "posts": "List<PostPage>"
}
```

#### **1.3 사용자 프로필 수정**
**PUT** `/users/profile/{user_id}`
```json
{
    "introduction": "String"
}
```
- **응답**: `200 OK`

#### **1.4 사용자 삭제**
**DELETE** `/users/{user_id}`
```json
{
    "password": "String",
    "renterPassword": "String"
}
```
- **응답**: `200 OK`

---

### 2. 게시물 관리
#### **2.1 게시물 생성**
**POST** `/feed`
```json
{
    "title": "String",
    "contents": "String"
}
```
- **응답**: `201 Created`

#### **2.2 게시물 조회**
**GET** `/feed/{post_id}`
- **응답**:
```json
{
    "title": "Stirng",
    "content": "String",
    "userName": "String",
    "updated_at": "datetime"
}
```

#### **2.3 게시물 수정**
**PATCH** `/feed/{post_id}`
```json
{
    "title": "String",
    "contents": "String"
}
```
- **응답**: `200 OK`

#### **2.4 게시물 삭제**
**DELETE** `/feed/{post_id}`
- **응답**: `200 OK`

---

### 3. 댓글 관리
#### **3.1 댓글 추가**
**POST** `/feed/{post_id}/comments`
```json
{
    "comments": "string"
}
```
- **응답**: `201 Created`

#### **3.2 게시물 댓글 조회**
**GET** `/feed/{post_id}/comments`
- **응답**:
```json
[
    {
        "commnets": "String",
        "like": "Long",
        "userName": "String",
        "updateAt": "datetime"
    }
]
```

#### **3.2 게시물 댓글 수정**
**GET** `/feed/{post_id}/comments/{comment_id}`
```json
[
    {
        "commnets": "String"
    }
]
```
- **응답**:
```json
[
    {
        "commnets": "String",
        "like": "Long",
        "userName": "String",
        "updateAt": "datetime"
    }
]
```

#### **3.3 댓글 삭제**
**DELETE** `/feed/{post_id}/comments/{comment_id}`
- **응답**: `200 OK`

---

### 4. 좋아요 관리
#### **4.1 게시물 좋아요/취소**
**POST** `/posts/{post_id}/likes`
```json
{
    "user_id": "integer",
    "like_status": "boolean"
}
```
- **응답**: `200 OK`

#### **4.2 댓글 좋아요/취소**
**POST** `/feed/{post_id}/comments/{commnet_id}/{user_id}/like`
- **응답**:
```json
{
    "LikeNum": "Long"
}
```
---

### 5. 팔로우 관리
#### **5.1 사용자 팔로우**
**POST** `/users/{user_id}/follow`
```json
{
    "follower_id": "integer",
    "is_approve": "boolean"
}
```
- **응답**: `200 OK`

#### **5.2 사용자 언팔로우**
**DELETE** `/users/follow/{user_id}`
- **응답**:
```json
[
    {
        "message": "String"
    }
]
```

#### **5.3 팔로워 목록 조회**
**GET** `/users/follow/{user_id}/followers`
- **응답**:
```json
[
    {
        "userName": "String",
        "follwers": "List<FollowUserInfoDto>"
    }
]
```

#### **5.4 팔로잉 목록 조회**
**GET**`/users/follow/{user_id}/followings`
- **응답**:
```json
[
    {
        "userName": "String",
        "followings": "List<FollowUserInfoDto"
    }
]
```

---

### 데이터 모델 개요
- **User**: 플랫폼 사용자를 나타냅니다.
- **Post**: 사용자가 작성한 게시물을 나타냅니다.
- **Comment**: 게시물에 대한 사용자의 댓글을 나타냅니다.
- **PostLike**: 게시물에 대한 좋아요를 추적합니다.
- **CommentLike**: 댓글에 대한 좋아요를 추적합니다.
- **Follow**: 사용자 간의 팔로우 관계를 추적합니다.
