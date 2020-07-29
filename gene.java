import java.util.*;
import java.lang.*;
import java.io.*;
import edu.duke.*;

/**
  * @author A.Manoj Kumar
  */

public class gene {
    
    //finds a particular codon's location(index)
    public int findcodon(String dna,int start, String stopgene){
        int currindex = dna.indexOf(stopgene,start);
        while(currindex != -1){
            if((currindex - start)% 3 ==0){
                return currindex ;
            }
            else{
                currindex = dna.indexOf(stopgene, currindex+1);
            }
        }
        return -1;
    }
    
    //finds a gene starting with ATG codon.
    public String findGene(String dna,int Start){
        int startIndex = dna.indexOf("ATG",Start);
        if(startIndex == -1){ //ATGATCTAATTTATGCTGCAACGGTGAAGA
            return "";
        }
        int taaIndex= findcodon(dna,startIndex,"TAA");
        int tagIndex= findcodon(dna,startIndex,"TAG");
        int tgaIndex= findcodon(dna,startIndex,"TGA");
        int minIndex =0;
        if(tgaIndex == -1 || (tagIndex != -1 && tagIndex <tgaIndex)){
            minIndex=tagIndex;
        }
        else{
            minIndex=tgaIndex;
        }
        if(minIndex == -1 || (taaIndex != -1 && taaIndex <minIndex)){
            minIndex= taaIndex;
        }
        if(minIndex == -1){
            return "";
        }
        return dna.substring(startIndex,minIndex +3);
    }
    
    //Prints a gene
    public void printGene(String dna) {
        int Start =0;
        while(true){
            String curgene = findGene(dna,Start);
            if(curgene.isEmpty()){
                break;
            }
            System.out.println("gene = "+curgene);
            Start = dna.indexOf(curgene, Start) + curgene.length();
        }
    }
    
    //cg ratio is the ratio between total number of "C" and "G" divided by the total length of the gene
    public void cgratioGene(String dna) {
        int Start =0;
        int count = 0;
        while(true){
            String curgene = findGene(dna,Start);
            int cg=0;
            for(int i=0;i<curgene.length();i++){
                if(curgene.charAt(i)=='C' || curgene.charAt(i)=='G'){
                    cg++;
                }
            }
            float ratio= (float)cg/curgene.length() ;
            if(curgene.isEmpty()){
                break;
            }
            System.out.println("cgratio = "+ratio);
            if(ratio>0.35000000){
                count++;
            }
            Start = dna.indexOf(curgene, Start) + curgene.length();
        }
        System.out.println("no of genes having cgratio greater than 0.35="+count);
    }
    
    //cg ratio of the entire DNA
    public void cgratioDna(String dna){
        int c=0,g=0;
        for(int i=0;i<dna.length();i++){
            if(dna.charAt(i) == 'C'){
                c++;
            }
            if(dna.charAt(i) == 'G'){
                g++;
            }
        }
        System.out.println("cgratio of DNA = "+((float)c+g)/dna.length());
    }
    
    //this method finds and counts a particular codon in the entire DNA
    public void findCTG(String dna){
        int start=0;
        int count=0;
        while(true){
            int ctgindex= dna.indexOf("CTG",start);
            if(ctgindex!=-1){
                count++;
            }
            else{
                break;
            }
            start = ctgindex + 3;
        }
        System.out.println("total no of CTG genes = "+count);
    }
    
    //this method prints the total number of genes present in the DNA
    public void printnumGene(String dna) {
        int Start =0;
        int count = 0;
        while(true){
            String curgene = findGene(dna,Start);
            if(curgene.isEmpty()){
                break;
            }
            count++;
            Start = dna.indexOf(curgene, Start) + curgene.length();
        }
        System.out.println("no. of genes = "+count);
    }
    
    public void testgene(){
        printGene("ATGxxxTGAxxxATGxxxTAA");
        printnumGene("ATGCTGTGACTGATGCTGTAA");
        cgratioGene("ATGCCCTGAxxxATGGGGTAA");
        cgratioDna("ATGCCCTGAGGGATGCCCTAA");
        findCTG("ATGCTGTGACTGATGCTGTAA");
    }
    
    //this method is used to read the DNA from the mentioned file
    public void testResource(){
        FileResource fr = new FileResource("brca1line.fa");
        String dna = fr.asString();
        System.out.println("dna= "+dna);
        printGene(dna);
    }
}
