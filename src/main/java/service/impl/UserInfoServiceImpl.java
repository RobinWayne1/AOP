package service.impl;

import service.UserInfoService;

/**
 * @author Robin
 * @date 2019/11/28 -18:51
 */
public class UserInfoServiceImpl implements UserInfoService
{
    @Override
    public void helloWorld()
    {
        System.out.println("UserInfoServiceHelloWorld!");
    }
}
