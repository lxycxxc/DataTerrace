
#sql("findAll")/*查询所有*/
 select * from #(tablename)
#end

#sql("findByid")/*根据id查询*/
 select * from #(tablename) where id = #para(id)
  #if(wheres!=null)
	 	#for(x:wheres)
		and #(x.key)
		#if(x.value!=null)
	 		=
	 		#if(x.value.indexOf("#")==0) 
				#(x.value.substring(1)) 
			#else 
				#para(x.value) 
			#end
		#end
    #end
  #end
#end


#sql("findByField")/*根据指定字段查询*/
 select * from #(tablename) where 1=1 
 	#for(x:wheres)
 		and #(x.key)
 		#if(x.value!=null)
	 		=
	 		#if(x.value.indexOf("#")==0) 
				#(x.value.substring(1)) 
			#else 
				#para(x.value) 
			#end
		#end
    #end
#end

#sql("findRandom")/*随机查询指定条数*/
 select * from (select * from #(tablename) order by dbms_random.value) where rownum<=#para(len)
#end