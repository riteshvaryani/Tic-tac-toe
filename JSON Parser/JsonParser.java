package taboola;
import java.util.*;
public class JsonParser {
    static int startIndex=0;
    public static void main(String args[]) {
        String input="{\"debug\" :\"on\",\"window\":{\"title\"  :   \"sample\",\"size\":500}}";
        //System.out.println(input.length());
        Map<String,Object> output = (Map<String,Object>)JsonParser.parse(input);
        assert output.get("debug").equals("on");
        assert ((Map<String,Object>) output.get("window")).get("title").equals("sample");
        assert ((Map<String,Object>) output.get("window")).get("size").equals("500");
    }
    
    public static Object parse(String json) {
        int len=json.length();
        int start=1;
        Object ans= getMap(json,start,len-1);
        Map<String,Object> ans1=(Map<String,Object>)ans;
        return ans;
    }
    public static Object getMap(String input, int start, int end) {
        Map<String, Object> fullMap= new HashMap<>();
        while(startIndex<=end) {
            String key=getKey(input,end);
            if(key.equals(""))
                break;
            Object value=getValue(input);
            fullMap.put(key,value);
        }
        return fullMap;
    }
    public static String getKey(String input, int end) {
        boolean endFlag=false;
        int stringStart=-1;
        int stringEnd=-1;
        boolean startFlag=false;
        for(int i=startIndex;i<=end;i++) {
            if(input.charAt(i)=='\"' && input.charAt(i-1)!='\\' && !startFlag) {
                stringStart=i+1;
                startFlag=true;
            }
            else if(input.charAt(i)=='\"' && input.charAt(i-1)!='\\') {
                stringEnd=i;
                startIndex=i+1;
                break;
            }
        }
        if(stringStart!=-1) {
        String withQuote=input.substring(stringStart, stringEnd+1).trim();
        return withQuote.substring(0,withQuote.length()-1);
        }
        else {
            startIndex=input.length();
            return new String();
        }
    }
    public static Object getValue(String input) {
        int start=startIndex;
        boolean endFlag=false;
        boolean quoteExist=false;
        int end=-1;
        Object obtain=null;
        int len=input.length();
        for(int i=startIndex;i<len;i++) {
            if(input.charAt(i)==' ' || input.charAt(i)==':')
                continue;
            if(input.charAt(i)=='{') {
                obtain= getMap(input,i+1,len-1);
                break;
            }
            else{
                start=i;
                if(input.charAt(start)=='\"') {
                    start++;
                    quoteExist=true;
                }          
                 for(int j=start;j<len;j++) {
                     if(quoteExist && input.charAt(j)=='\"' && input.charAt(j-1)!='\\') {
                         endFlag=true;
                         end=j-1;
                         startIndex=j+1;
                         break;
                     }
                     if(!quoteExist && (input.charAt(j)>'9' || input.charAt(j)<'0')) {   
                         end=j-1;
                         startIndex=j;
                         break;
                     }
                 }
                 obtain=(String)input.substring(start,end+1);
                 break;            
            }
        }
        return obtain;
    }   
}