# UserpostControllerApi

All URIs are relative to **

Method | HTTP request | Description
------------- | ------------- | -------------
[**addNewUserpostUsingPOST**](UserpostControllerApi.md#addNewUserpostUsingPOST) | **POST** /posts/post | Create a new post
[**decrementPostCountUsingPUT**](UserpostControllerApi.md#decrementPostCountUsingPUT) | **PUT** /posts/post/decrement/{userpostid} | Decrements the vote count of a given post and returns updated post
[**deleteUserpostByIdUsingDELETE**](UserpostControllerApi.md#deleteUserpostByIdUsingDELETE) | **DELETE** /posts/post/{userpostid} | Delete a post of given id
[**findAllOtherUserpostsExceptMineUsingGET**](UserpostControllerApi.md#findAllOtherUserpostsExceptMineUsingGET) | **GET** /posts/otherposts | Returns all Posts not created by current user
[**findUserpostsByCurrentLocationUsingGET**](UserpostControllerApi.md#findUserpostsByCurrentLocationUsingGET) | **GET** /posts/currentlocation | Returns all Posts filtered by the location the current user is registered as
[**findUserpostsByLocationUsingGET**](UserpostControllerApi.md#findUserpostsByLocationUsingGET) | **GET** /posts/location/{location} | Returns all Posts of a given location
[**findUserpostsByUserNameUsingGET**](UserpostControllerApi.md#findUserpostsByUserNameUsingGET) | **GET** /posts/myposts | Returns all Posts made by current user
[**getUserPostByIdUsingGET**](UserpostControllerApi.md#getUserPostByIdUsingGET) | **GET** /posts/post/{userpostid} | Return post of given id
[**incrementPostCountUsingPUT**](UserpostControllerApi.md#incrementPostCountUsingPUT) | **PUT** /posts/post/increment/{userpostid} | Increments the vote count of a given post and returns updated post
[**listAllUserpostsUsingGET**](UserpostControllerApi.md#listAllUserpostsUsingGET) | **GET** /posts/allposts | Returns all Posts
[**updatePostUsingPUT**](UserpostControllerApi.md#updatePostUsingPUT) | **PUT** /posts/post/{userpostid} | Updates and returns post of given id


## **addNewUserpostUsingPOST**

Create a new post

### Example
```bash
 addNewUserpostUsingPOST
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **newuserpost** | [**Userpost**](Userpost.md) | newuserpost |

### Return type

[**Userpost**](Userpost.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **decrementPostCountUsingPUT**

Decrements the vote count of a given post and returns updated post

### Example
```bash
 decrementPostCountUsingPUT userpostid=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userpostid** | **integer** | Post Id |

### Return type

[**Userpost**](Userpost.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **deleteUserpostByIdUsingDELETE**

Delete a post of given id

### Example
```bash
 deleteUserpostByIdUsingDELETE userpostid=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userpostid** | **integer** | Post Id |

### Return type

**map**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **findAllOtherUserpostsExceptMineUsingGET**

Returns all Posts not created by current user

### Example
```bash
 findAllOtherUserpostsExceptMineUsingGET
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**array[Userpost]**](Userpost.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **findUserpostsByCurrentLocationUsingGET**

Returns all Posts filtered by the location the current user is registered as

### Example
```bash
 findUserpostsByCurrentLocationUsingGET
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**array[Userpost]**](Userpost.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **findUserpostsByLocationUsingGET**

Returns all Posts of a given location

### Example
```bash
 findUserpostsByLocationUsingGET location=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location** | **string** | Location |

### Return type

[**array[Userpost]**](Userpost.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **findUserpostsByUserNameUsingGET**

Returns all Posts made by current user

### Example
```bash
 findUserpostsByUserNameUsingGET
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**array[Userpost]**](Userpost.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **getUserPostByIdUsingGET**

Return post of given id

### Example
```bash
 getUserPostByIdUsingGET userpostid=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userpostid** | **integer** | Post Id |

### Return type

[**Userpost**](Userpost.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **incrementPostCountUsingPUT**

Increments the vote count of a given post and returns updated post

### Example
```bash
 incrementPostCountUsingPUT userpostid=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userpostid** | **integer** | Post Id |

### Return type

[**Userpost**](Userpost.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **listAllUserpostsUsingGET**

Returns all Posts

### Example
```bash
 listAllUserpostsUsingGET
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**array[Userpost]**](Userpost.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **updatePostUsingPUT**

Updates and returns post of given id

### Example
```bash
 updatePostUsingPUT userpostid=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **updatedPost** | [**Userpost**](Userpost.md) | updatedPost |
 **userpostid** | **integer** | Post Id |

### Return type

[**Userpost**](Userpost.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

