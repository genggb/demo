####1、org.apache.commons.net.util.Base64加密换行问题
        public static void main(String[] args) throws Exception {
            String oldsign = "XJuUzGif1/WY4F9McqNCBbx14b2AdOAxnw3QUm+XeNi8eZ1MmQmRsUkTmfFNles9XX1Y/CETjVL3sQbW/PFWLA==";
            String sign = "001eyJwYXljb2RlIjoxNTk5NjE3MjQzOTAxLCJWaXJ0dWFsQWNjdCI6IlZpcnR1YWxBY2N0IiwiVmlydHVhbE9wQmtOYW1lIjoiVmlydHVhbE9wQmtOYW1lIiwiVmlydHVhbE5hbWUiOiJWaXJ0dWFsTmFtZSJ91599617243971";
            String newsign = org.apache.commons.codec.binary.Base64.encodeBase64String(WuXiYyUtils.encryptToByte(sign,"SHA-512"));
            System.out.println("生成的加密验签oldsign："+oldsign);
            System.out.println("生成的加密验签newsign："+newsign);
            if(oldsign.equals(newsign)){
                System.out.println("加密结果正确1");
            }else{
                System.out.println("加密结果错误1");
            }
    
            newsign = org.apache.commons.net.util.Base64.encodeBase64String(WuXiYyUtils.encryptToByte(sign,"SHA-512"));
            System.out.println("生成的加密验签newsign："+newsign);
            if(oldsign.equals(newsign)){
                System.out.println("加密结果正确2");
            }else{
                System.out.println("加密结果错误2");
            }
        }
        
-   执行结果（org.apache.commons.net.util.Base64会生成2个换行符）
    
            生成的加密验签oldsign：XJuUzGif1/WY4F9McqNCBbx14b2AdOAxnw3QUm+XeNi8eZ1MmQmRsUkTmfFNles9XX1Y/CETjVL3sQbW/PFWLA==
            生成的加密验签newsign：XJuUzGif1/WY4F9McqNCBbx14b2AdOAxnw3QUm+XeNi8eZ1MmQmRsUkTmfFNles9XX1Y/CETjVL3sQbW/PFWLA==
            加密结果正确1
            生成的加密验签newsign：XJuUzGif1/WY4F9McqNCBbx14b2AdOAxnw3QUm+XeNi8eZ1MmQmRsUkTmfFNles9XX1Y/CETjVL3
            sQbW/PFWLA==
                
            加密结果错误2
-   据RFC 822规定，每76个字符，还需要加上一个回车换行

            建议使用org.apache.commons.codec.binary.Base64替换