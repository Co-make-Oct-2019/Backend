# PostcommentControllerApi

All URIs are relative to **

Method | HTTP request | Description
------------- | ------------- | -------------
[**addNewPostcommentUsingPOST**](PostcommentControllerApi.md#addNewPostcommentUsingPOST) | **POST** /comments/comment/{userpostid} | Create a new comment
[**deletePostcommentByIdUsingDELETE**](PostcommentControllerApi.md#deletePostcommentByIdUsingDELETE) | **DELETE** /comments/comment/{postcommentid} | Delete a comment
[**getPostCommentUsingGET**](PostcommentControllerApi.md#getPostCommentUsingGET) | **GET** /comments/comment/{postcommentid} | View a comment with given id
[**updatePostCommentUsingPUT**](PostcommentControllerApi.md#updatePostCommentUsingPUT) | **PUT** /comments/comment/{postcommentid} | Update a comment with given id


## **addNewPostcommentUsingPOST**

Create a new comment

### Example
```bash
 addNewPostcommentUsingPOST userpostid=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **newpostcomment** | [**Postcomment**](Postcomment.md) | newpostcomment |
 **userpostid** | **integer** | userpostid |

### Return type

[**Postcomment**](Postcomment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **deletePostcommentByIdUsingDELETE**

Delete a comment

### Example
```bash
 deletePostcommentByIdUsingDELETE postcommentid=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **postcommentid** | **integer** | Comment Id |

### Return type

**map**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **getPostCommentUsingGET**

View a comment with given id

### Example
```bash
 getPostCommentUsingGET postcommentid=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **postcommentid** | **integer** | Comment Id |

### Return type

[**Postcomment**](Postcomment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **updatePostCommentUsingPUT**

Update a comment with given id

### Example
```bash
 updatePostCommentUsingPUT postcommentid=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **postcommentid** | **integer** | Comment Id |
 **updatedPostcomment** | [**Postcomment**](Postcomment.md) | updatedPostcomment |

### Return type

[**Postcomment**](Postcomment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

