(ns geocoder.test.maxmind
  (:use geocoder.maxmind
        clojure.test))

(def ip-address "92.229.192.11")

(deftest test-geocode-ip
  (when *service*
    (is (nil? (geocode-ip "127.0.0.1")))
    (let [result (geocode-ip ip-address)]
      (is (= "Germany" (:name (:country result))))
      (is (= "de" (:iso-3166-1-alpha-2 (:country result))))
      (is (= "16" (:id (:region result))))
      (is (= "Berlin" (:city result)))
      (is (= 52.516693115234375 (:latitude (:location result))))
      (is (= 13.399993896484375 (:longitude (:location result))))
      (is (= 0 (:area-code result)))
      (is (= 0 (:dma-code result)))
      (is (= 0 (:metro-code result))))))

(deftest test-wrap-maxmind
  (when *service*
    (let [response ((wrap-maxmind identity) {:remote-addr ip-address})]
      (is (= (geocode-ip ip-address) (:maxmind response))))))