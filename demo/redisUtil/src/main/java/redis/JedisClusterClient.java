package redis;import redis.clients.jedis.*;import java.util.HashSet;import java.util.Set;/** * Created by wengqian on 2017/4/10. * 操作 redis 集群 */public class JedisClusterClient {    private static int count = 0;    private static final JedisClusterClient redisClusterClient = new JedisClusterClient();    /**     * 私有构造函数     */    private JedisClusterClient() {}    public static JedisClusterClient getInstance() {        return redisClusterClient;    }    private static JedisPoolConfig getPoolConfig(){        JedisPoolConfig config = new JedisPoolConfig();        config.setMaxTotal(1000);        config.setMaxIdle(100);        config.setTestOnBorrow(true);        return config;    }    public static Set<HostAndPort> jedisClusterNodes;    public void SaveRedisCluster() {        jedisClusterNodes = new HashSet<HostAndPort>();        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7000));        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7001));        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7002));        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7003));        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7004));        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7005));//        JedisCluster jc = new JedisCluster(jedisClusterNodes,getPoolConfig());//        jc.set("cluster", "this is a redis cluster");//        String result = jc.get("cluster");//        System.out.println(result);    }    public static void main(String[] args) {        JedisClusterClient jedisClusterClient = JedisClusterClient.getInstance();        jedisClusterClient.SaveRedisCluster();        JedisCluster jc = new JedisCluster(jedisClusterNodes,getPoolConfig());//        jc.set("cluster", "this is a redis cluster");        String result = jc.get("cluster");        System.out.println(result);    }}