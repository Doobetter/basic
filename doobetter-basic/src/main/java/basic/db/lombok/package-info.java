/**
 * Lombok 安装、入门 - 消除冗长的 java 代码

前言：
    逛开源社区的时候无意发现的，用了一段时间，觉得还可以，特此推荐一下。
    lombok 提供了简单的注解的形式来帮助我们简化消除一些必须有但显得很臃肿的 java 代码。特别是相对于 POJO，光说不做不是我的风格，先来看看吧。

lombok 的官方网址：http://projectlombok.org/  

lombok 其实到这里我就介绍完了，开个玩笑，其实官网上有 lombok 三分四十九秒的视频讲解，里面讲的也很清楚了，而且还有文档可以参考。
在这里我就不扯太多，先来看一下 lombok 的安装，其实这个官网视频上也有讲到啦

lombok 安装
    使用 lombok 是需要安装的，如果不安装，IDE 则无法解析 lombok 注解。先在官网下载最新版本的 JAR 包，现在是 0.11.2 版本，我用的是 0.11.0
    第一次使用的时候我下载的是最新版本的，也就是我现在用的 0.11.0，到现在已经更新了两个版本，更新的好快啊 ... ...

1. 双击下载下来的 JAR 包安装 lombok
    我选择这种方式安装的时候提示没有发现任何 IDE，所以我没安装成功，我是手动安装的。如果你想以这种方式安装，请参考官网的视频。

2.eclipse / myeclipse 手动安装 lombok
    1. 将 lombok.jar 复制到 myeclipse.ini / eclipse.ini 所在的文件夹目录下
    2. 打开 eclipse.ini / myeclipse.ini，在最后面插入以下两行并保存：
        -Xbootclasspath/a:lombok.jar
        -javaagent:lombok.jar
    3.重启 eclipse / myeclipse

lombok 注解：
    lombok 提供的注解不多，可以参考官方视频的讲解和官方文档。
    Lombok 注解在线帮助文档：http://projectlombok.org/features/index.
    下面介绍几个我常用的 lombok 注解：
        @Data   ：注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
        @Setter：注解在属性上；为属性提供 setting 方法
        @Getter：注解在属性上；为属性提供 getting 方法
        @Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
        @NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
        @AllArgsConstructor：注解在类上；为类提供一个全参的构造方法
 */
/**
 * @author YAO
 *
 */
package basic.db.lombok;