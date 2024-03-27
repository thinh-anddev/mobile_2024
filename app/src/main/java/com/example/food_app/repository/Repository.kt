package com.example.food_app.repository

import com.example.food_app.R
import com.example.food_app.model.Food

class Repository {
    companion object {
        private var listFood: MutableList<Food> = ArrayList()
        fun listFood(): MutableList<Food> {

            listFood.add(
                Food(
                    1,"Pizza gà",1231.0,"Pizza","con","Pizza gà có lẽ vô cùng quen thuộc với dân nghiện pizza bởi tính phổ thông và dễ ăn của loại pizza này." +
                            " Thịt gà là một món ăn bổ dưỡng, giúp gia tăng vị giác của bất cứ món ăn nào mà chúng góp mặt. " +
                            "Đặc biệt những bạn đam mê gà thì không thể bỏ qua chiếc bá", R.drawable.pizzaga,10)
            )

            listFood.add(
                    Food(
                            2,"Pizza bò băm",1200.0,"Pizza","con","Vị chua chua, ngọt ngọt của dứa kết hợp với vỏ bánh giòn xốp cùng với thịt bò băm có thể tạo nên hương vị pizza đặc trưng khiến bạn nhớ mãi chẳng quên. Cũng bởi vậy mà pizza bò băm luôn là \"best-seller\" trong menu của các tiệm bánh pizza nổi tiếng. Bạn hoàn toàn có thể thực hiện làm món ngon này ngay tại nhà để phục vụ những người thương yêu trong gia đình mình.", R.drawable.pizzabobam,10)
            )
            listFood.add(
                    Food(
                            3,"Pizza hải sản",1300.0,"Pizza","con","Mùa hạ như thôi thúc con người tìm đến những hương vị của biển cả. Chiếc pizza này có hương vị mặn, ngọt cân bằng. Khi ăn, thực khách sẽ cảm nhận được vị tươi ngon từ hải sản, vị béo ngậy của phô mai, vị thơm của vỏ bánh. Tín đồ của hải sản chắc chắn sẽ không thể bỏ lỡ chiếc pizza này.", R.drawable.pizzahaisan,10)
            )
            listFood.add(
                    Food(
                            4,"Pizza margherita",1500.0,"Pizza","con","Pizza margherita được coi là chiếc bánh nổi tiếng nhất thế giới do một đầu bếp vùng Naples, Italy sáng tạo ra với cái tên được lấy cảm hứng từ nữ hoàng Margherita . Pizza margherita là hiện thân của mùa hạ với hương vị đậm đà với sự tươi mới của sốt cà chua, độ dai dai của phomai mozzarella. Loại bánh này mang một hương vị rất riêng với các loại nguyên liệu tươi rói được tuyển chọn kỹ lưỡng chắc chắn sẽ làm bạn hài lòng", R.drawable.pizzamargherita,10)
            )
            listFood.add(
                    Food(
                            5,"Pizza nhân nhồi",1231.0,"Pizza","con","Khác với các loại pizza thông thường thì pizza nhân nhồi có kích thước khổng lồ với phần đế dày, nhiều nhân và  lượng phô mai Mozzarella của bánh nhiều gấp 3 lần các loại khác, hòa quyện cùng thịt và nước sốt thực sự là không thể cưỡng lại. Vì vậy, chỉ mới xuất hiện nhưng loại bánh này đã thu hút một lượng người hâm mộ không hề nhỏ, luôn trở thành món ăn đắt khách tại các quầy pizza", R.drawable.pizzanhannhoi,10)
            )
            listFood.add(
                    Food(
                            6,"Pizza pepperoni",1231.0,"Pizza","con","Pepperonis là một loại xúc xích chế biến đặc trưng từ các loại thịt bò hoặc thịt heo, loại xúc xích đặc biệt này được hòa trộn cùng với nhau kèm theo ớt. Pepperonis tạo nên hương vị thơm ngon hoàn hảo cho chiếc bánh pizza đặc biệt, với sự kết hợp hoàn hảo giữa phần đế bánh mỏng cùng với độ giòn tan cùng với phần nhân được phủ thêm một lớp xúc xích tỏi ớt xông khói cay cay. Được trang trí bắt mắt mang đậm tinh thần Mỹ. Khi ăn bạn sẽ cảm nhận rõ được vị mằn mặn, cay nhẹ đặc trưng của xúc xích cay, hòa quyện với vị béo ngậy của phô mai và vị chua ngọt của sốt cà chua", R.drawable.pizzapepperoni,10)
            )
            listFood.add(
                    Food(
                            7,"Sữa tươi trân châu đường đen",1231.0,"drinks","con","Sữa tươi trân châu đường đen – một cái tên quen thuộc đang gây bão phủ sóng với giới trẻ. Từ khi bắt đầu lan rộng từ Sài Gòn, đến nay món trà sữa có cái tên quen thuộc nhưng mùi vị cực kỳ mới lạ này vẫn chưa hề hạ nhiệt", R.drawable.suatuoitranchauduongden,100)
            )
            listFood.add(
                    Food(
                            8,"Trà phô mai kem sữa",1231.0,"drinks","con","Hương vị lạ miệng từ sự hòa quyện của vị phô mai đặc trưng, của lớp kem sữa béo béo ngọt ngọt và tất nhiên cả vị chát nhẹ hơi thanh của trà chắc chắn sẽ mê hoặc mọi thực khách", R.drawable.tra_pho_mai_kem_sua,10)
            )
            listFood.add(
                    Food(
                            9,"Trà hoa quả",1231.0,"drinks","con","Cầm trong tay cốc trà hoa quả mát lạnh trang trí bằng những lát cam, lát chanh, lát đào… đầy màu sắc cũng khiến người thưởng thức thích thú hơn.", R.drawable.tra_hoa_qua,10)
            )
            listFood.add(
                    Food(
                            10,"Matcha đá xay",1231.0,"drinks","con","Matcha đá xay có vị hơi ngọt, sệt như kem và có vị chát nhẹ đặc trưng của trà xanh, dịu nhẹ và ngát hương. Với hương vị tươi mới, thuần khiết của trà xanh và sự mát lạnh từ đá xay", R.drawable.matchatraxanh,10)
            )
            listFood.add(
                    Food(
                            11,"Trà đào chanh sả",1231.0,"drinks","con","Trà đào chanh sả, như chính tên gọi của mình vậy, có vị đậm ngọt thanh của đào, có vị chua chua dịu nhẹ của chanh, có mùi thơm của sả.", R.drawable.tra_dao_chanh_sa,10)
            )
            listFood.add(
                    Food(
                            12,"Trà hoa quả nhiệt đới",1231.0,"drinks","con","Trà hoa quả nhiệt đới tại mỗi quán nước uống có thể sẽ khác nhau về các loại hoa quả, nhưng thông thường sẽ là những loại quả phổ biến như thanh long, xoài, lê, đào, táo", R.drawable.tra_hoa_qua,10)
            )
            listFood.add(
                    Food(
                            13,"Trà sữa gạo rang Hàn Quốc",1231.0,"drinks","con","Trà sữa gạo rang là món nước uống rất tốt cho sức khỏe, chống oxi hóa, chống ung thư, tốt cho tim mạch và đặc biệt là có công dụng lớn trong việc làm đẹp, trắng mịn làn da, vì vậy món đồ uống này đặc biệt được các chị em yêu thích", R.drawable.tra_sua_gao_rang_han_quoc,10)
            )
            listFood.add(
                    Food(
                            14,"Trà sữa Hokkaido",1231.0,"drinks","con","Thị trường nước uống trà sữa năm 2020 vẫn nóng và khốc liệt với sự có mặt thêm của hàng chục món trà sữa mùi vị mới mẻ. Và trà sữa hokkaido với vị caramel ngọt béo vừa phải chính là món đồ uống mới lạ đang được nhiều bạn trẻ chú ý", R.drawable.tra_sua_hokkaido,10)
            )
            listFood.add(
                    Food(
                            15,"Snack khoai tây",1231.0,"snacks","con","Hương vị: Gia vị luôn được tẩm ướp đậm đà, vừa miệng trong mỗi gói snack, tuy nhiên bạn cần chọn ra được hương vị phù hợp với sở thích của mình. Snack hiện nay có đủ các vị chua, cay, mặn, ngọt, béo,… để bạn thỏa thích lựa chọn", R.drawable.snackkhoaitay,10)
            )
            listFood.add(
                    Food(
                            16,"Snack vị cua",1231.0,"snacks","con","Snack vị cua sốt chua ngọt Oishi Crab Me 35g (từ 3 tuổi)", R.drawable.snackcua,10)
            )
            listFood.add(
                    Food(
                            17,"Snack rong biển",1231.0,"snacks","con","Công thức rong biển chiên, nướng đặc trưng giòn rụm, tan nhanh trong miệng, khử được mùi khó chịu của rong biển khi ăn và đọng lại dư vị mặn ngọt hòa quyện.", R.drawable.snackrongbien,10)
            )
            listFood.add(
                    Food(
                            18,"Snack khoai tây",1231.0,"snacks","con","Snack khoai tây vị thăn bò nướng Texas Lays 90g", R.drawable.snackkhoaitay,10)
            )
            listFood.add(
                    Food(
                            19,"Snack vị gà",1231.0,"snacks","con","Snack vị gà quay da giòn Poca 35g (từ 1 tuổi)", R.drawable.snackga,10)
            )
            listFood.add(
                    Food(
                            20,"Snack Orion",1231.0,"Pizza","con","Bánh que chấm socola vị dâu Chocho 35g", R.drawable.snackorion,10)
            )
            listFood.add(
                    Food(
                            21,"Snack Lay’s Stax",1231.0,"snacks","con","Snack khoai tây vị tự nhiên Lays 100g (từ 1 tuổi)", R.drawable.snacklaystak,10)
            )
            listFood.add(
                    Food(
                            22,"Sốt sa tế",1231.0,"sauce","con","Sa tế tôm dùng tạo màu cho món ăn, tẩm ướp thịt hay ăn cùng với các món nước như bún, hủ tiếu, phở…", R.drawable.sotsate,10)
            )
            listFood.add(
                    Food(
                            23,"Sốt chua ngọt",1231.0,"sauce","con","Sốt chua ngọt dùng nhiều trong các món xào như: thịt gà sốt chua ngọt, sườn heo sốt chua ngọt, tôm sốt chua ngọt… giúp món ăn thấm đều gia vị và đậm đà hơn thay vì nem nêm từng loại gia vị riêng biệt", R.drawable.sotchuangot,10)
            )
            listFood.add(
                    Food(
                            24,"Sốt xí muội",1231.0,"sauce","con","Sốt xí muội cực kỳ thích hợp dùng chấm các loại rau củ luộc hay các món thịt chiên, hải sản nướng… cho hương vị thơm đậm đà", R.drawable.sotximuoi,10)
            )
            listFood.add(
                    Food(
                            25,"Sốt dầu hào",1231.0,"sauce","con","Sốt dầu hào thích hợp để rưới lên đĩa rau xào có thêm phần tỏi phi giúp tăng hương vị cho món ăn", R.drawable.sotdauhao,10)
            )
            listFood.add(
                    Food(
                            26,"Sốt cà chua",1231.0,"sauce","con","Sốt cà chua có màu đỏ mận tự nhiên giúp các món thịt băm sốt cà, cá sốt cà… trở nên đẹp mắt và đậm vị hơn", R.drawable.sotcachua,10)
            )
            listFood.add(
                    Food(
                            27,"Sốt tương ớt",1231.0,"sauce","con","Sốt tương ớt được ưa chuộng cho nhiều món ăn, có thể thêm vào nước chấm khác tạo độ sệt và chua cay lạ miệng -  thêm vào các món nước như bún, phở, hủ tiếu - dùng chấm các món chiến, rán…", R.drawable.sottuongot,10)
            )
            listFood.add(
                    Food(
                            28,"Sốt me",1231.0,"sauce","con","Sốt me được dùng làm gia vị các các món sườn sốt me, tôm rim me, ghẹ/ cánh gà rang me… Khi chế biến, tùy vào món ăn mà có thể phi thơm sả ớt rồi cho sốt me vào, thêm nước và cho tiếp nguyên liệu chính vào xào đến sệt", R.drawable.sotme,10)
            )
            listFood.add(
                    Food(
                            29,"Sốt muối ớt chanh",1231.0,"sauce","con","Sốt muối ớt chanh dùng chấm hải sản hay thịt gà luộc/ hấp/ nướng đều ngon bá cháy", R.drawable.sotmuoiotchanh,10)
            )
            listFood.add(
                    Food(
                            30,"Sốt chao",1231.0,"sauce","con","Sốt chao dùng chấm thịt nướng, ăn cùng bánh xèo hay các món gỏi đều ngon", R.drawable.sotchao,10)
            )
            listFood.add(
                    Food(
                            31,"Cơm nắm muối vừng",1231.0,"rice","con","Món này mà ăn cùng với chà bông hoặc chả lụa là ngon hết sẩy. Bạn có thể thực hiện món này vào buổi sáng để làm món điểm tâm sáng đầy dinh dưỡng và năng lượng nhé!", R.drawable.comnammuoivung,10)
            )
            listFood.add(
                    Food(
                            32,"Cơm nắm rau củ",1231.0,"Pizza","con","Nếu bạn muốn món cơm có thêm chất xơ thì cơm nắm rau củ chính là sự lựa chọn phù hợp nhất cho bạn đấy", R.drawable.comnamraucu,10)
            )
            listFood.add(
                    Food(
                            33,"Cơm nắm nấm đông cô", 3.99, "rice", "con", "Những ai yêu thích hương vị của nấm đông cô thì công thức làm cơm nắm nấm đông cô này là đúng bài rồi đó!", R.drawable.comnamdongco, 100)
            )
            listFood.add(
                    Food(
                            34,"Cơm nắm Nhật Bản", 3.99, "rice", "con", "Từng hạt cơm thơm, dẻo bao bọc và quyện đều phần nhân cá ngừ Mayo béo ngậy, quấn bên ngoài là miếng rong biển mằn mặn hấp dẫn biết bao.", R.drawable.comnamcahoinhatban, 100)
            )
            listFood.add(
                    Food(
                            35,"Cơm nắm thịt heo chiên xù", 3.99, "rice", "con", "Sự hòa quyện giữa cơm trắng dẻo thơm và thịt heo chiên xù ngoài giòn, trong mềm ẩm, được tẩm ướp đậm đà của món cơm nắm thịt heo chiên xù này chắc chắn sẽ khiến bạn thích mê.", R.drawable.comnamthitheo, 100)
            )
            listFood.add(
                    Food(
                            36,"Cơm nắm thịt heo xào và spam", 3.99, "rice", "con", "Với thịt heo xào đậm đà xen lẫn chút cay nhẹ của ớt, kết hợp với spam mềm ngọt và lá tía tô thơm lừng, món cơm nắm thịt heo xào và spam ngon ngất ngây này sẽ khiến bạn mê mẩn đấy.", R.drawable.comnamspam, 100)
            )
            listFood.add(
                    Food(
                            37,"Cơm nắm trứng lòng đào", 3.99, "rice", "con", "Trứng lòng đào là món ăn béo ngậy, dễ làm lại giàu dinh dưỡng, nay kết hợp với cơm nắm đậm đà cùng kim chi cay giòn thì lại càng hao cơm hơn.", R.drawable.comnamtrunglongdao, 100)
            )
            listFood.add(
                    Food(
                            38,"Cơm nắm thịt heo xào và spam", 3.99, "rice", "con", "Với thịt heo xào đậm đà xen lẫn chút cay nhẹ của ớt, kết hợp với spam mềm ngọt và lá tía tô thơm lừng, món cơm nắm thịt heo xào và spam ngon ngất ngây này sẽ khiến bạn mê mẩn đấy.", R.drawable.comnamspam, 100)
                    )
            listFood.add(
                    Food(
                            39,"Cơm nắm thịt bò", 3.99, "rice", "con", "Cơm nắm dẻo, bên ngoài thì nóng giòn kết hợp với thịt bò xào mềm, đậm đà bên trong mang đến cho bạn một trải nghiệm vị giác cực kì thú vị đấy nhé!", R.drawable.comnamtrungteya, 100)
                    )
            listFood.add(
                    Food(
                            40,"Cơm nắm thịt heo xào và spam", 3.99, "rice", "con", "Với thịt heo xào đậm đà xen lẫn chút cay nhẹ của ớt, kết hợp với spam mềm ngọt và lá tía tô thơm lừng, món cơm nắm thịt heo xào và spam ngon ngất ngây này sẽ khiến bạn mê mẩn đấy.", R.drawable.comnamspam, 100)
                    )
            listFood.add(
                    Food(
                            41,"Cơm nắm cá ngừ", 3.99, "rice", "con", "Cơm nắm cá ngừ là món mà các tín đồ mê cơm nắm không thể bỏ qua. Cá ngừ bùi béo kết hợp cùng cơm dẻo thơm lừng mùi mè trắng. Dù chỉ là những nguyên liệu đơn giản thôi nhưng đủ sức tạo nên món ăn với hương vị số 1.", R.drawable.comnamcangu, 100)
                    )
            listFood.add(
                    Food(
                            42,"Cơm nắm cá hồi Nhật Bản", 3.99, "rice", "con", "Sẽ thật thiếu sót nếu món cơm nắm cá hồi Nhật Bản thơm béo, dinh dưỡng không xuất hiện trong danh sách lần này.", R.drawable.comnamcahoinhatban, 100)
                    )
            listFood.add(
                    Food(
                            43,"Cánh gà chiên nước mắm", 3.99, "chicken", "con", "Cánh gà chiên nước mắm từ lâu đã trở thành món ăn yêu thích của nhiều gia đình, nhất là các gia đình có em nhỏ. Phần cánh gà được phủ đều lớp nước sốt mắm đường mang màu sắc nâu bóng bắt mắt, tạo nên hương vị mằn mặn ngọt ngọt đậm đà.", R.drawable.canhganuocmam, 100)
                    )
            listFood.add(
                    Food(
                            44,"Cánh gà chiên giòn", 3.99, "chicken", "con", "Khi nhắc đến các cách chế biến cánh gà truyền thống thì không thể thiếu công thức làm cánh gà chiên giòn đơn giản mà hấp dẫn. Cánh gà sau khi được làm sạch và khử mùi hôi một cách hoàn hảo được mang đi tẩm ướp đều cùng với gia vị và một chút bột chiên giòn.", R.drawable.canhgachienxu, 100)
                    )
            listFood.add(
                    Food(
                            45,"Cơm nắm thịt heo xào và spam", 3.99, "rice", "con", "Với thịt heo xào đậm đà xen lẫn chút cay nhẹ của ớt, kết hợp với spam mềm ngọt và lá tía tô thơm lừng, món cơm nắm thịt heo xào và spam ngon ngất ngây này sẽ khiến bạn mê mẩn đấy.", R.drawable.comnamthitheo, 100)
                    )
            listFood.add(
                    Food(
                            46,"Cánh gà chiên xù", 3.99, "chicken", "con", "Cánh gà chiên xù với chút biến tấu nhẹ nhàng tạo nên sự tương phản nổi trội về hương vị, dễ dàng thu hút sự yêu thích của người dùng.", R.drawable.canhgachienxu, 100)
                    )
            listFood.add(
                    Food(
                            47,"Cánh gà chiên sốt chanh dây", 3.99, "chicken", "con", "Với phần cánh gà được chiên sơ qua giúp lớp da bên ngoài giòn giòn, bên trong vừa chín tới, giữ được vị ngon ngọt vốn có của thịt gà. Sau cùng, rưới đều lên trên phần nước sốt chanh dây thơm phức, xen lẫn chút vị cay cay của tương ớt, chút vị chua nhẹ của tương cà thêm ít vị ngọt từ mật ong.", R.drawable.canhgachiensotchanhday, 100)
                    )
            listFood.add(
                    Food(
                            48,"Cánh gà chiên coca", 3.99, "chicken", "con", "Cánh gà chiên coca mang màu sắc caramel óng mượt, bắt mắt người dùng ngay từ lần đầu nhìn thấy. Từng chiếc cánh gà như được phủ đều phần nước sốt coca lạ miệng, thoang thoảng mùi thơm đặc trưng của từng gia vị được nêm nếm cẩn thận.", R.drawable.canhgachiencoca, 100)
                    )
            listFood.add(
                    Food(
                            49,"Cánh gà chiên bơ tỏi", 3.99, "chicken", "con", "Chỉ với các thành phần chính từ cánh gà được ướp đều trong gia vị, tỏi băm phi vàng cùng với bơ được làm tan chảy. Vị bùi bùi cùng mùi thơm nồng của tỏi phi giúp gợi lên hương vị mềm ngon vốn có của thịt gà, thớ thịt mềm ngọt, cùng hương vị beo béo của bơ, cuốn hút đến bất ngờ.", R.drawable.canhgachienbotoi, 100)
                    )
            listFood.add(
                    Food(
                            50,"Cánh gà chiên mật ong", 3.99, "chicken", "con", "Vị ngọt lịm của mật ong khi kết hợp cùng với ớt bột cay nồng, tạo nên sự tương phản rõ nét về hương vị. Nước sốt ngọt ngọt cay cay như thấm đều trong từng miếng thịt mềm ngon. Ăn với cơm nóng thì tuyệt vời nhé.", R.drawable.canhgachiensotmatong, 100)
                    )
            listFood.add(
                    Food(
                            51,"Cánh gà chiên sốt me", 3.99, "chicken", "con", "Tất cả cánh gà sau khi được chiên vừa chín tới, lớp da bên ngoài chuyển sang một màu vàng, lại được đảo đều cùng với phần nước sốt me có trộn lẫn thêm một chút đường, tiêu, một ít bột ngọt để làm gia tăng thêm mùi vị đặc sắc cho món ăn.", R.drawable.canhgachiensotme, 100)
            )
            listFood.add(
                    Food(
                            52,"Cánh gà chiên nhồi cơm", 3.99, "chicken", "con", "Một cách chế biến lạ mà ngon mang đậm màu sắc văn hóa ẩm thực Đài Loan. Những miếng thịt gà có vẻ ngoài bóng mượt, da gà chuyển sang màu vàng nâu đẹp mắt, bên trong lại là phần cơm chiên được khéo léo bao bọc lại bên trong.", R.drawable.canhgachiencom, 100)
            )
            listFood.add(
                    Food(
                            53,"Cánh gà chiên lolipop que kẹo", 3.99, "chicken", "con", "Cánh gà chiên lollipop mang vẻ ngoài hút mắt cùng với hương vị tuyệt vời chắc chắn sẽ trở thành món ăn yêu thích của bạn ngay từ lần đầu thực hiện thử đấy.", R.drawable.canhgachienlopipop, 100)
            )
            listFood.add(
                    Food(
                            54,"Cánh gà chiên sốt Thái", 3.99, "chicken", "con", "Món cánh gà chiên sốt Thái nổi bật với hương vị mang nét riêng chuyên biệt của phần nước sốt bắt vị. Phần nước cốt chanh trộn đều cùng nước cốt me, thêm đường để cân bằng mùi vị, thêm chút nước mắm giúp gia tăng sự đậm đà.", R.drawable.canhgachiensotthai, 100)
            )
            listFood.add(
                    Food(
                            55,"Cánh gà chiên sốt trứng muối", 3.99, "chicken", "con", "Một sự kết hợp tuyệt vời từ hương vị trứng muối và cánh gà, vị mặn mặn mà trứng muối, beo béo từ bơ lạt, thêm mùi thơm thoang thoảng từ tỏi băm phi thơm" , R.drawable.canhgachiensottrungmuoi, 100)
            )

            listFood.add(
                    Food(
                            56,"Cánh gà chiên sữa tươi", 3.99, "chicken", "con", "Sữa tươi thơm béo, ngọt lịm được dùng để ngâm cánh gà, giúp cánh gà mềm mại, thớ thịt mềm thơm, không bị khô cứng khi chiên. Kết hợp với phần bột chiên được trộn lẫn giữa bột mì, bột ớt, một ít tiêu xay, chiên lên vàng ươm, giòn rụm ăn là ghiền.", R.drawable.canhgachiensuatuoi, 100)
            )
            listFood.add(
                    Food(
                            57,"Khoai tây nghiền", 3.99, "potato", "con", "Với cách làm đơn giản, món khoai tây nghiền vô cùng mịn màng, thơm và béo ngậy, dùng làm món ăn chơi hay ăn kèm đều ngon hết sẩy.", R.drawable.khoaitaynghien, 100)
            )
            listFood.add(
                    Food(
                            58,"Snack khoai tây", 3.99, "potato", "con", "Snack chiên xong giòn rụm, bên ngoài phủ lớp gia vị mặn mặn ngọt ngọt, tuy đơn giản nhưng nhâm nhi mãi không chán.", R.drawable.snackkhoaitay, 100)
            )
            listFood.add(
                    Food(
                            59,"Bánh khoai tây", 3.99, "potato", "con", "Bánh có lớp ngoài giòn thơm, cắn vào cảm nhận được phần nhân có vị sữa béo béo quyện với phần khoai mềm dẻo. Đảm bảo cả nhà sẽ phải mê mẫn món ngon này cho xem", R.drawable.banhkhoaitay, 100)
            )
            listFood.add(
                    Food(
                            60,"Canh khoai tây", 3.99, "potato", "con", "Khoai tây được nấu cho mềm dẻo, vị ngọt từ thịt giúp cho nước dùng thêm đậm đà, Món này dù ăn với cơm hay ăn không cũng đều ngon tuyệt.", R.drawable.canhkhoaitay, 100)
            )
            listFood.add(
                    Food(
                            61,"Khoai tây xào", 3.99, "potato", "con", "Khoai tây xào xong vẫn giữ được vị bùi bùi, mềm nhưng không bị nát. Nêm nếm gia vị cho vừa ăn là đảm bảo vô cùng hao cơm đó bạn!", R.drawable.khoaitayxao, 100)
            )
            listFood.add(
                    Food(
                            62,"Khoai tây lắc phô mai", 3.99, "potato", "con", "Không cần ra ngoài hàng mua bạn vẫn có thể thưởng thức món khoai tây lắc phô mai tại nhà, từng cọng khoai tây giòn tan, lớp ngoài vàng đều được phủ lớp phô mai mặn mặn ngọt ngọt, thơm nức mũi. Cắn vào bên trong vẫn giữa được độ ẩm, mềm và dẻo ngọt rất ngon.", R.drawable.khoaitaylacphomai, 100)
            )
            listFood.add(
                    Food(
                            63,"Khoai tây nướng", 3.99, "potato", "con", "Khoai tây nướng có lớp ngoài vàng ươm giòn giòn, bên trong nóng hổi bùi bùi, không sợ bị nhiều chất béo. Món ăn sẽ tỏa mùi hương thơm lừng khiến bạn không cưỡng lại được.", R.drawable.khoaitaynuong, 100)
            )
            listFood.add(
                    Food(
                            64,"Khoai tây đút lò", 3.99, "potato", "con", "Món này dùng để ăn chơi hay đãi tiệc đều rất thích hợp, đảm bảo sẽ khiến thực khách phải xao xuyến.", R.drawable.khoaitaydutlo, 100)
            )
            listFood.add(
                    Food(
                            65,"Súp khoai tây", 3.99, "potato", "con", "Súp khoai tây dễ nấu, thơm ngon bổ dưỡng, đặc biệt chay mặn đều dùng được. Súp mềm mịn, sền sệt, nóng hổi thơm lừng mùi khoai tây, rất thích hợp cho các bé ăn dặm hay nấu cho cả nhà ăn sáng đấy!", R.drawable.supkhoaitay, 100)
            )
            listFood.add(
                    Food(
                            66,"Salad khoai tây", 3.99, "potato", "con", "Với cách làm này sẽ hạn chế được lượng dầu mỡ mà khoai vẫn giữ được độ ngọt tự nhiên, khoai tây kết hợp với trứng bùi bùi quyện với nước sốt đậm đà cực ngon miệng.", R.drawable.saladkhoaitay, 100)
            )
            listFood.add(
                    Food(
                            67,"Khoai tây chiên cay", 3.99, "potato", "con", "Bánh chiên xong vô cùng giòn, cắn vào là thấy giòn rụm trong miệng, vị cay cay the the của ớt khiến vị giác bạn bị kích thích, cứ muốn ăn thêm vài cái nữa cho mà xem.", R.drawable.khoaitaychiencay, 100)
            )
            listFood.add(
                    Food(
                            68,"Khoai tây chiên sốt phô mai", 3.99, "potato", "con", "Khoai tây sau khi chiên vàng giòn mà chấm vào sốt phô mai này thì quá tuyệt vời, đảm bảo ai ăn cũng phải tấm tắc khen ngon.", R.drawable.khoaitaylacphomai, 100)
            )
            listFood.add(
                    Food(
                            69,"Gnocchi khoai tây", 3.99, "potato", "con", "Gnocchi khi làm xong vô cùng thơm với phần khoai tây nóng hổi, khoai bùi bùi mềm mềm kết hợp với phần bột dai dai, khi ăn chấm kèm tương ớt và tương cà thì chẳng mấy chốc mà vơi.", R.drawable.gnocchikhoaitay, 100)
            )

            return listFood
        }
    }
}