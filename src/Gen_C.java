import java.io.*;
import java.util.*; //為了套用StringTokenizer和TreeMap的元件

class Gen_C{

	char[][] C;     
    int length=0;
    
    
    
    //check_c的功用為「確認此候選集的子集在前一階段是否為頻繁項目集」	        
    //ElementaryTable Preceding_LItemsets = new ElementaryTable();	
	

    //產生「經過判斷後的新候選集」
    ElementaryTable New_CItemsets = new ElementaryTable();
    
    
    
	                
	public Gen_C(char[][] C, ElementaryTable Preceding_LItemsets, int length, double[] maxGainThreshold) {  //建立的同時設定大小基準 (排序過的k-1erasable itemset,k-1的erasable itemset, 目前第k階段)

			
             for(int i=0;i<C.length-1;i++)
             {
             		if(C[i]==null)break;
                  		
      				  for(int j=i+1;j<C.length;j++)	
      				  {
       					  char[] str1 = new char[C[i].length];
       					  char[] str2 = new char[C[j].length];
       						
       					  char[] temp;
       					  System.arraycopy(C[i],0,str1,0,str1.length); //arraycopy(來源, 起始索引, 目的, 起始索引, 複製長度)
       					  System.arraycopy(C[j],0,str2,0,str2.length);

       							//檢查與合併
       							if (CBC(str1,str2)){ //檢查
       								if (maxGainThreshold[str1[str1.length-1]] >= maxGainThreshold[str2[str2.length-1]] ) 
       									temp=Combine(str1,str2);  //合併
       								else
       									temp=Combine(str2,str1);
       								
       								if(temp==null)
       									break;
       								
       								if (Prune(temp,length, Preceding_LItemsets, maxGainThreshold))  ///修剪階段：將子項目集中出現非頻繁的項目去除    							       							      							     						       									      									
       									New_CItemsets.add(temp, 0); ///若該項目集通過以上兩階段，則存入C-length中
  							}

                         	}
                  	}

                  
   }


   
//==================== 修剪的方法 ====================


	
	public static boolean CBC(char[] a, char[] b) {// ComBine Check
		if (a.length!=b.length) return false;
		for (int i=0; i<a.length-1; i++)  //最後一個字元不檢查
			if (a[i]!=b[i])
				return false;
		return true;
	}
	
	public static char[] Combine(char[] a, char[] b) {  //合併
		char[] c = new char[a.length+1];
		System.arraycopy(a,0,c,0,a.length);//arraycopy(來源, 起始索引, 目的, 起始索引, 複製長度)
		System.arraycopy(b,b.length-1,c,c.length-1,1); //a全部+b最後一個
		
		
		
		
		return c;
	}

	
	public static boolean Prune(char[] a,int length, ElementaryTable Preceding_LItemsets, double[] maxGainThreshold) //(generate candidate,item數,k-1的erasable itemset)
	{
       		for (int k=0; k<a.length; k++) 
       		{
       			//產生所有(k-1)子項目集
       			char[] subset = new char[a.length-1];
       			if (k!=0)    //前半
       				System.arraycopy(a,0,subset,0,k); 
       			if (k!=(a.length-1))   //後半
       				System.arraycopy(a,k+1,subset,k,(length-k-1));   //abc k=0:bc, K=2:ab
       			//檢查
       			if (subset[0] == a[0] || maxGainThreshold[a[0]] == maxGainThreshold[a[1]])
       			{
       				if (Preceding_LItemsets.NoContainsKey(subset)) {
       				/*for(int i=0;i<tmp.length;i++)
       					System.out.print(""+(int)tmp[i]+", ");
       				System.out.println();*/
       				return false;
       				}      				
       			}
       			
       		}
       		
       		return true;
	}
 


	public ElementaryTable get_CItemsets() {     //取得New_CItemsets
		return New_CItemsets;
	}





 
  
}