import java.util.*;
import java.io.*;
import java.lang.*;
//to make dictionary

class DirList 
{

String dirname = "F:/sem 5/java/challenging assignment/ex6DataEmails_train";

void make(TreeSet ts,HashMap hm_spam,HashMap hm_nonspam) throws Exception
{
HashMap hm;
File f1 = new File(dirname);
BufferedReader br;
if(f1.isDirectory())
{
System.out.println("Directory of " + dirname);
String s[] = f1.list();
	for (int i=0; i < s.length; i++) 
	{
	
	File f = new File(dirname + "/" + s[i]);
	if(s[i].equals("spam-train"))
	{
		hm=hm_spam;
		System.out.println("spam"+f);
		Thread.sleep(1000);
	}
	else
	{
		hm=hm_nonspam;
		System.out.println("nonspam"+f);
		Thread.sleep(1000);
	}	
		if (f.isDirectory())
		{
		String s1[] = f.list();
		for(int j=0;j<s1.length;j++)
		{
		String str;
		File f2=new File(dirname+"/"+s[i]+"/"+s1[j]);
		StringTokenizer tr;
		
		br=new BufferedReader(new FileReader(f2));
			while((str=br.readLine())!=null)
			{
			tr=new StringTokenizer(str," ");
			while(tr.hasMoreTokens())
			{
			String word=tr.nextToken();
			ts.add(word);
				boolean b1=hm.containsKey(word);
					if(b1==true)
					{
					hm.put(word,(Double)hm.get(word)+1d);
					}
					else
					hm.put(word,1d);	
			}
			}
		System.out.println(f2+ " is a file" + j+" "+i);
		}
		System.out.println(s.length+" "+s1.length);
		}
		
		
	}
} 
System.out.print(ts+" ");
System.out.println(ts.size());

System.out.print(hm_spam+" ");
System.out.println(hm_spam.size());
System.out.print(hm_nonspam+" ");
System.out.println(hm_nonspam.size());
}//end of make

}//end of class



class CondProb
{
void calc(TreeSet ts,ArrayList spam,ArrayList nonspam,HashMap hm_spam,HashMap hm_nonspam) 
{
int c=0;
Double count1=0d;
Double count2=0d;
int size=ts.size();
Set mapSet = (Set) hm_spam.entrySet();
Iterator mapIterator = mapSet.iterator();
        	while (mapIterator.hasNext()) 
        	{			
        	Map.Entry mapEntry = (Map.Entry) mapIterator.next();
        	String keyValue = (String) mapEntry.getKey();
        	Double value = (Double) mapEntry.getValue();
	count1+=value;
	}		
Set mapSet1 = (Set) hm_nonspam.entrySet();
Iterator mapIterator1 = mapSet1.iterator();
        	while (mapIterator1.hasNext()) 
        	{			
        	Map.Entry mapEntry = (Map.Entry) mapIterator1.next();
        	String keyValue = (String) mapEntry.getKey();
        	Double value = (Double) mapEntry.getValue();
	count2+=value;
	}				
System.out.println(count1+" "+count2);	
	Iterator itr = ts.iterator();
	
		while(itr.hasNext()) 
		{		
		Object word= itr.next();
		//System.out.println(element);
		Double d,d1;	
		if(hm_spam.containsKey(word))
		{
		d=((Double)hm_spam.get(word)+1d)/(count1+size);
		}
		else
		d=1/(count1+size);
		spam.add(d);

		if(hm_nonspam.containsKey(word))
		{
		d1=((Double)hm_nonspam.get(word)+1d)/(count2+size);
		}
		else
		d1=1/(count2+size);
		nonspam.add(d1);
		}
System.out.println("done");	
System.out.println(spam);
System.out.println(nonspam);
}
}//end of CondProb


class Input extends DirList
{

StringTokenizer tr;
Double sum=0d;

void ipCal(TreeSet ts,ArrayList spam,ArrayList nonspam,Double prior_spam,Double prior_nonspam) throws Exception
{
BufferedReader br=new BufferedReader(new FileReader("F:/sem 5/java/challenging assignment/ex6DataEmails_test/spam-test/spmsga27.txt"));
String str;
int c=0;
Object arr_spam[] = spam.toArray();
Object arr_nonspam[]=nonspam.toArray();
Double s1=prior_spam;
Double s2=prior_nonspam;
Iterator itr = ts.iterator();
	while((str=br.readLine())!=null)
	{
	tr=new StringTokenizer(str," ");
		while(tr.hasMoreTokens())
		{
		String word=tr.nextToken();
		c=0;
			while(itr.hasNext())
			{
			Object element=itr.next();
			boolean bl=(word).equals(element);
				if(bl==true)
				{
				break;
				}
				
				if(bl==true)
				break;
				c++;
			}  
		Double res,res1;
		if((res=s1*(Double)arr_spam[c])>0.0)
		{
		s1*=(Double)arr_spam[c];
		System.out.println(s1);
		//Thread.sleep(1000);
		}
		if((res1=s2*(Double)arr_nonspam[c])>0.0)
		{
		s2*=(Double)arr_nonspam[c];
		}
		
		}

	}
System.out.println(s1+" "+s2);
if(s1>s2)
System.out.println("spam");
else
System.out.println("nonspam");
}//end of ipCal
}//end of input


class Demo
{
public static void main(String args[]) //throws Exception
{
HashMap hm_spam=new HashMap();
HashMap hm_nonspam=new HashMap();
TreeSet ts=new TreeSet();

Double prior_spam=0.5d;
Double prior_nonspam=0.5d;

ArrayList spam=new ArrayList();
ArrayList nonspam=new ArrayList();

DirList dl=new DirList();
CondProb cp=new CondProb();
Input in=new Input();

	
try
{
dl.make(ts,hm_spam,hm_nonspam);
cp.calc(ts,spam,nonspam,hm_spam,hm_nonspam);
in.ipCal(ts,spam,nonspam,prior_spam,prior_nonspam);
}
catch(Exception e)
{}
//System.out.println(ts);
}
}