package service.impl;

import service.UserService;

/**
 * @author Robin
 * @date 2019/11/27 -11:25
 */

public class UserServiceImpl implements UserService
{
    @Override
    public void hello()
    {
        System.out.println("hello world!");
    }

    @Override
    public void hello(String word)
    {
        System.out.println(word);
    }
}
