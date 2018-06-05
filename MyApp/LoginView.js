import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  TextInput,
  View,
  Image,
  Animated,
  TouchableOpacity
} from 'react-native';

class LogoTitle extends Component {
	render(){
		return (
			<Image
				source={require('./images/spiro.png')}
				style={{width: 30, height: 30 }}
			/>
		);
	}
}	

export default class LoginView extends Component{
	constructor(props){
		super(props);
		this.state = {
			name: '',
			password: '',
			fadeAnim: new Animated.Value(0),
		};
	}
	
	componentDidMount() {
		Animated.timing(     // 随时间变化而执行的动画类型
		  this.state.fadeAnim,    // 动画中的变量值
		  {
			toValue: 1,           // 透明度最终变为1，即完全不透明
		  }
		).start();                // 开始执行动画
    }
  
	static navigationOptions = ({navigation, navigationOptions}) => {
		const { params } = navigation.state;
		
		return {
			headerTitle: <LogoTitle />,
			/* These values are used instead of the shared configuration! */
			headerStyle: {
				backgroundColor: navigationOptions.headerTintColor,
			},
			headerTintColor: navigationOptions.headerStyle.backgroundColor,
		}
	};	
	login(){
		const name = this.state.name;
		const password = this.state.password;
		console.log("用户名: " + name + ", 密码: " + password);
		this.props.navigation.navigate('Main', {
			userId: name
		});
	}
	
	render(){
		return (
			<View>
				<Image style={styles.back} source={require('./images/login/banner_2.png')}/>
				<View style={[styles.back, styles.mask]}></View>
				<View style={styles.loginView}>
					<View style={styles.bannerWrap}>
						<Image style={styles.bg} source={require('./images/login/banner_1.png')}/>
						<View style={styles.logoTextWrap}>
							<Animated.Text style={[styles.logoText, {opacity: this.state.fadeAnim}]}>Demo</Animated.Text>
						</View>
						<View style={styles.copyRightWrap}>
							<Text style={styles.copyRightText}>©2018</Text>
						</View>
					</View>
					<View style={styles.inputWrap}>
						<Text style={styles.inputTitle}>SIGN IN</Text>
						<TextInput 
							onChangeText={(text) => this.setState({name: text})} />
						<TextInput 
							onChangeText={(text) => this.setState({password: text})} />
						<Animated.View style={{opacity: this.state.fadeAnim}}>
							<TouchableOpacity
								style={styles.loginBtn}
								onPress={this.login.bind(this)}
							>
								<Text style={styles.loginBtnText}>登录</Text>
							</TouchableOpacity>
						</Animated.View>
					</View>
					<View style={styles.footer}>
						<Image style={styles.footerLogo} source={require('./images/login/react_native_logo.png')} />
						<Text style={styles.footerText}>Powered by React-Native</Text>
					</View>
				</View>
			</View>
		);
	}
}

const height = 100;
const refRat = 1;
const styles = StyleSheet.create({
    back: {
        position: 'absolute',
        top: 0,
        bottom: 0,
        left: 0,
        right: 0,
        height: height - 24
    },
    mask: {
        backgroundColor: '#2B75DE',
        opacity: 0.2
    },
    loginView: {
        height: height - 24
    },
    bannerWrap: {

    },
    bg: {
        width: 375 * refRat,
        height: 235 * refRat
    },
    logoTextWrap: {
        position: 'absolute',
        bottom: 90
    },
    logoText: {
        width: 375 * refRat,
        textAlign: 'center',
        fontSize: 40,
        color: '#fff'
    },
    copyRightWrap: {
        position: 'absolute',
        bottom: 0,
        paddingTop: 6,
        paddingBottom: 6,
        width: 375 * refRat,
        borderTopWidth: 1,
        borderTopColor: '#fff',
        opacity: 0.5
    },
    copyRightText: {
        textAlign: 'center',
        fontSize: 12,
        color: '#fff'
    },
    inputWrap: {
        alignItems: 'center'
    },
    inputTitle: {
        paddingTop: 5,
        paddingBottom: 20,
        textAlign: 'center',
        fontSize: 12,
        color: '#fff',
        opacity: 0.5
    },
    input: {
        width: 230 * refRat,
        textAlign: 'center',
        color: '#fff',
        borderBottomWidth: 1,
        borderBottomColor: '#fff',
    },
    loginBtn: {
        marginTop: 30,
        padding: 10,
        width: 90 * refRat,
        alignItems: 'center',
        borderRadius: 20,
        backgroundColor: '#FF8161'
    },
    loginBtnText: {
        color: '#fff'
    },
    footer: {
        position: 'absolute',
        bottom: 0,
        width: 375 * refRat,
        alignItems: 'center'
    },
    footerLogo: {
        width: 20 * refRat,
        height: 20 * refRat
    },
    footerText: {
        marginTop: 5,
        textAlign: 'center',
        fontSize: 12,
        color: '#fff'
    }
});

