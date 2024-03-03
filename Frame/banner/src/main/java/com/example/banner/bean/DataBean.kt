package com.example.banner.bean

import com.example.banner.R
import java.util.Locale
import java.util.Random

class DataBean(val imageRes: Int = 0,val imageUrl: String = "", val title: String?, val viewType: Int) {

    companion object {
        fun getTestData(): List<DataBean> {
            val list = ArrayList<DataBean>()
            list.apply {
                add(DataBean(R.drawable.image1, title = "相信自己,你努力的样子真的很美", viewType = 1))
                add(DataBean(R.drawable.image2, title = "极致简约,梦幻小屋", viewType = 3))
                add(DataBean(R.drawable.image3, title = "超级卖梦人", viewType = 3))
                add(DataBean(R.drawable.image4, title = "夏季新搭配", viewType = 1))
                add(DataBean(R.drawable.image5, title = "绝美风格搭配", viewType = 1))
                add(DataBean(R.drawable.image6, title = "微微一笑 很倾城", viewType = 3))
            }
            return list
        }

        fun getTestData2(): List<DataBean> {
            val list = ArrayList<DataBean>()
            list.apply {
                add(DataBean(R.drawable.image7, title = "听风.赏雨", viewType = 3))
                add(DataBean(R.drawable.image8, title = "迪丽热巴.迪力木拉提", viewType = 1))
                add(DataBean(R.drawable.image9, title = "爱美.人间有之", viewType = 3))
                add(DataBean(R.drawable.image10, title = "洋洋洋.气质篇", viewType = 1))
                add(DataBean(R.drawable.image11, title = "生活的态度", viewType = 3))
            }
            return list
        }

        /**
         * 仿淘宝商品详情第一个是视频
         *
         */
        fun getTestDataVideo(): List<DataBean> {
            val list = ArrayList<DataBean>()
            list.apply {
                add(DataBean(imageUrl = "http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4", title = "第一个放视频", viewType = 2))
                add(DataBean(R.drawable.image7, title = "听风.赏雨", viewType = 1))
                add(DataBean(R.drawable.image8, title = "迪丽热巴.迪力木拉提", viewType = 1))
                add(DataBean(R.drawable.image9, title = "爱美.人间有之", viewType = 1))
                add(DataBean(R.drawable.image10, title = "洋洋洋.气质篇", viewType = 1))
                add(DataBean(R.drawable.image11, title = "生活的态度", viewType = 1))
            }
            return list
        }

        fun getTestData3(): List<DataBean> {
            val list = ArrayList<DataBean>()
            list.apply {
                add(DataBean(imageUrl = "https://img.zcool.cn/community/013de756fb63036ac7257948747896.jpg", title = null, viewType = 1))
                add(DataBean(imageUrl = "https://img.zcool.cn/community/01639a56fb62ff6ac725794891960d.jpg", title = null, viewType = 1))
                add(DataBean(imageUrl = "https://img.zcool.cn/community/01270156fb62fd6ac72579485aa893.jpg", title = null, viewType = 1))
                add(DataBean(imageUrl = "https://img.zcool.cn/community/01233056fb62fe32f875a9447400e1.jpg", title = null, viewType = 1))
                add(DataBean(imageUrl = "https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg", title = null, viewType = 1))
            }
            return list
        }

        fun getVideos(): List<DataBean> {
            val list = ArrayList<DataBean>()
            list.apply {
                add(DataBean(imageUrl = "http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4", title = null, viewType = 0))
                add(DataBean(imageUrl = "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4", title = null, viewType = 0))
                add(DataBean(imageUrl = "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4", title = null, viewType = 0))
                add(DataBean(imageUrl = "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4", title = null, viewType = 0))
                add(DataBean(imageUrl = "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4", title = null, viewType = 0))
                add(DataBean(imageUrl = "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4", title = null, viewType = 0))
            }
            return list
        }

        fun getColors(size: Int): List<String> {
            val list = ArrayList<String>()
            for (i in 0 until size) {
                list.add(getRandColor())
            }
            return list
        }

        /**
         * 获取十六进制的颜色代码.例如  "#5A6677"
         * 分别取R、G、B的随机值，然后加起来即可
         *
         * @return String
         */
        fun getRandColor(): String {
            val random = Random()
            var R = Integer.toHexString(random.nextInt(256)).uppercase(Locale.ROOT)
            var G = Integer.toHexString(random.nextInt(256)).uppercase(Locale.ROOT)
            var B = Integer.toHexString(random.nextInt(256)).uppercase(Locale.ROOT)

            R = if (R.length == 1) "0$R" else R
            G = if (G.length == 1) "0$G" else G
            B = if (B.length == 1) "0$B" else B

            return "#$R$G$B"
        }
    }
}