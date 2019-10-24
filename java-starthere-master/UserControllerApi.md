# UserControllerApi

All URIs are relative to **

Method | HTTP request | Description
------------- | ------------- | -------------
[**addNewUserUsingPOST**](UserControllerApi.md#addNewUserUsingPOST) | **POST** /newuser/createnewuser | Create a new user
[**deleteCurrentUserUsingDELETE**](UserControllerApi.md#deleteCurrentUserUsingDELETE) | **DELETE** /users/user | Delete current user
[**getCurrentUserInfoUsingGET**](UserControllerApi.md#getCurrentUserInfoUsingGET) | **GET** /users/getuserinfo | returns info for current
[**getUserByIdUsingGET**](UserControllerApi.md#getUserByIdUsingGET) | **GET** /users/user/{userId} | Returns a user of given userid
[**getUserByNameUsingGET**](UserControllerApi.md#getUserByNameUsingGET) | **GET** /users/user/name/{userName} | Returns a user of given name
[**getUserLikeNameUsingGET**](UserControllerApi.md#getUserLikeNameUsingGET) | **GET** /users/user/name/like/{userName} | returns all Users with names containing a given string
[**listAllUsersUsingGET**](UserControllerApi.md#listAllUsersUsingGET) | **GET** /users/users | returns all Users
[**updateUserUsingPUT**](UserControllerApi.md#updateUserUsingPUT) | **PUT** /users/user/profile/edit | Update and return info for current user


## **addNewUserUsingPOST**

Create a new user

### Example
```bash
 addNewUserUsingPOST
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **newuser** | [**User**](User.md) | newuser |

### Return type

**map**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **deleteCurrentUserUsingDELETE**

Delete current user

### Example
```bash
 deleteCurrentUserUsingDELETE
```

### Parameters
This endpoint does not need any parameter.

### Return type

**map**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **getCurrentUserInfoUsingGET**

returns info for current

### Example
```bash
 getCurrentUserInfoUsingGET
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **getUserByIdUsingGET**

Returns a user of given userid

### Example
```bash
 getUserByIdUsingGET userId=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **integer** | User Id |

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **getUserByNameUsingGET**

Returns a user of given name

### Example
```bash
 getUserByNameUsingGET userName=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userName** | **string** | Exact Username |

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **getUserLikeNameUsingGET**

returns all Users with names containing a given string

### Example
```bash
 getUserLikeNameUsingGET userName=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userName** | **string** | Username |

### Return type

[**array[User]**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **listAllUsersUsingGET**

returns all Users

### Example
```bash
 listAllUsersUsingGET  page=value  size=value  Specify as:  sort=value1 sort=value2 sort=...
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | [**Object**](.md) | Results page you want to retrieve (0..N) | [optional]
 **size** | [**Object**](.md) | Number of records per page. | [optional]
 **sort** | [**array[string]**](string.md) | Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported. | [optional]

### Return type

[**array[User]**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **updateUserUsingPUT**

Update and return info for current user

### Example
```bash
 updateUserUsingPUT
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **updateUser** | [**User**](User.md) | updateUser |

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

