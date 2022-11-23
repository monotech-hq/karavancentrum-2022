
(ns site.components.frontend.impressum.views
    (:require [elements.api     :as elements]
              [random.api       :as random]
              [re-frame.api     :as r]
              [x.dictionary.api :as x.dictionary]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn impressum
  ; @param (keyword) component-id
  ; @param (map) component-props
  [component-id component-props]
  (let [server-year       @(r/subscribe [:x.core/get-server-year])
        website-impressum @(r/subscribe [:x.db/get-item [:site :website-impressum]])]
       [:div {:id :mt-impressum}
             [:div {:id :mt-impressum--company-data}
                   (if-let [company-name (:company-name website-impressum)]
                           [:div {:id :mt-impressum--company-name}
                                 (if-let [company-est-year (:company-est-year website-impressum)]
                                         (let [current-year (:current-year website-impressum)]
                                              [:div {:id :mt-impressum--company-est-year}
                                                    (str company-est-year"-"server-year)]))
                                 (str company-name)])
                   (if-let [company-reg-office (:company-reg-office website-impressum)]
                           [:div {:id :mt-impressum--company-reg-office}
                                 (x.dictionary/looked-up :company-reg-office)
                                 (str ": " company-reg-office)])
                   (if-let [company-reg-no (:company-reg-no website-impressum)]
                           [:div {:id :mt-impressum--company-reg-no}
                                 (x.dictionary/looked-up :company-reg-no)
                                 (str ": " company-reg-no)])
                   (if-let [company-vat-no (:company-vat-no website-impressum)]
                           [:div {:id :mt-impressum--company-vat-no}
                                 (x.dictionary/looked-up :vat-no)
                                 (str ": " company-vat-no)])
                   (if-let [company-eu-vat-no (:company-eu-vat-no website-impressum)]
                           [:div {:id :mt-impressum--company-eu-vat-no}
                                 (x.dictionary/looked-up :eu-vat-no)
                                 (str ": " company-eu-vat-no)])]]))

(defn component
  ; @param (keyword)(opt) component-id
  ; @param (map) component-props
  ;  {}
  ;
  ; @usage
  ;  [impressum {...}]
  ;
  ; @usage
  ;  [impressum :my-impressum {...}]
  ([component-props]
   [component (random/generate-keyword) component-props])

  ([component-id component-props]
   [impressum component-id component-props]))
