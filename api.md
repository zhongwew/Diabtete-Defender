# API Documentation

## 1. Suggest
### URL

| URL | Method |
|---|---|
| /suggest | GET |

### Request: N/A

### Response

| Parameter | Description |
|---|---|
| type | Response type (success / fail) |
| suggest | suggestions |

Example

```json
{
  "type": "success",
  "suggest": {
      "exercise" : 10
  }
}
```

---

## 2. Send glucose level data

### URL

| URL | Method |
|---|---|
| /sugar | POST |

### Request

| Parameter | Description |
|---|---|
| Time | current time |
| Level | glucose level |

Example

```json
{
  "Time": "2017-11-24 17:30:00",
  "Level": 9.0
}
```

### Response

| Parameter | Description |
|---|---|
| type | Response type (success / fail) |


Example

```json
{
  "type": "success"
}
```