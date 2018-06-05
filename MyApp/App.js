import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View
} from 'react-native';
import { StackNavigator } from 'react-navigation';
import LoginView from './LoginView';
import About from './About';
import Main from './Main';

// 注册导航
const Navs = StackNavigator({
	Login: { screen: LoginView },
	About: {
		screen: About, // 必须，其他都非必须
	},
	Main: {
		screen: Main
	},
}, {
	initialRouteName: 'Login',
	// 屏幕导航的默认选项，也可以在组件内用static navigations设置
	navigationOptions: {
		headerStyle: {
			backgroundColor: '#f4511e',
		},
		headerTintColor: '#fff',
		headerTitleStyle: {
			fontWeight: 'bold',
		},
	},
	mode: 'card',  // 页面切换模式，左右是card，上下是modal
	headerMode: 'screen',  // 导航栏显示模式，screen：有渐变透明效果，float: 无透明效果，none: 隐藏导航栏
});

export default class App extends Component{
	render(){
		return <Navs />;
	}
}